package com.dwarfeng.fdr.impl.handler.resetter;

import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import com.dwarfeng.subgrade.stack.service.Service;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 Dubbo 微服务实现的重置器。
 *
 * @author DwArFeng
 * @since 1.11.0
 */
@Component
public class DubboResetter extends AbstractResetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboResetter.class);

    private final ApplicationContext ctx;

    private final RegistryConfig registry;
    private final ProtocolConfig protocol;

    private final Lock lock = new ReentrantLock();

    private ServiceConfig<DubboResetService> dubboService = null;
    private boolean startFlag = false;

    public DubboResetter(ApplicationContext ctx, RegistryConfig registry, ProtocolConfig protocol) {
        this.ctx = ctx;
        this.registry = registry;
        this.protocol = protocol;
    }

    @Override
    public void start() throws HandlerException {
        lock.lock();
        try {
            LOGGER.info("Dubbo resetter 开启...");

            // 判断启动状态，如已经启动，则什么也不做。
            if (startFlag) {
                return;
            }

            // 获取接口的实现。
            DubboResetService dubboResetService = ctx.getBean(DubboResetService.class);

            // 将接口注册为服务。
            ServiceConfig<DubboResetService> dubboService = new ServiceConfig<>();
            dubboService.setRegistry(registry);
            dubboService.setProtocol(protocol);
            dubboService.setInterface(DubboResetService.class);
            dubboService.setRef(dubboResetService);

            // 暴露服务。
            dubboService.export();

            // 缓存服务。
            this.dubboService = dubboService;

            // 状态位置位。
            startFlag = true;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() throws HandlerException {
        lock.lock();
        try {
            LOGGER.info("Dubbo resetter 停止...");

            // 判断启动状态，如已经停止，则什么也不做。
            if (!startFlag) {
                return;
            }

            // 取消暴露服务。
            dubboService.unexport();

            // 状态位置位。
            startFlag = false;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    public interface DubboResetService extends Service {

        /**
         * 重置记录功能。
         *
         * <p>
         * 因为 Dubbo 广播响应机制无法处理 void 返回类型，所以方法需要返回一个结果。
         *
         * @return 恒为 true。
         * @throws ServiceException 服务异常。
         */
        boolean resetRecord() throws ServiceException;

        /**
         * 重置映射功能。
         *
         * <p>
         * 因为 Dubbo 广播响应机制无法处理 void 返回类型，所以方法需要返回一个结果。
         *
         * @return 恒为 true。
         * @throws ServiceException 服务异常。
         */
        boolean resetMap() throws ServiceException;
    }

    @org.springframework.stereotype.Service
    public class DubboResetServiceImpl implements DubboResetService {

        private final ServiceExceptionMapper sem;

        public DubboResetServiceImpl(ServiceExceptionMapper sem) {
            this.sem = sem;
        }

        @Override
        public boolean resetRecord() throws ServiceException {
            try {
                LOGGER.info("接收到记录功能重置消息, 正在重置记录功能...");
                context.resetRecord();
                return true;
            } catch (Exception e) {
                throw ServiceExceptionHelper.logAndThrow("发生异常", LogLevel.WARN, sem, e);
            }
        }

        @Override
        public boolean resetMap() throws ServiceException {
            try {
                LOGGER.info("接收到映射功能重置消息, 正在重置映射功能...");
                context.resetMap();
                return true;
            } catch (Exception e) {
                throw ServiceExceptionHelper.logAndThrow("发生异常", LogLevel.WARN, sem, e);
            }
        }
    }
}