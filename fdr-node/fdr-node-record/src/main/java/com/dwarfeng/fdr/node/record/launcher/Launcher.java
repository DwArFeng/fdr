package com.dwarfeng.fdr.node.record.launcher;

import com.dwarfeng.fdr.node.record.handler.LauncherSettingHandler;
import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.fdr.stack.service.ResetQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

            // 拿出程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
            ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

            // 判断是否开启记录服务。
            long startRecordDelay = launcherSettingHandler.getStartRecordDelay();
            RecordQosService recordQosService = ctx.getBean(RecordQosService.class);
            if (startRecordDelay == 0) {
                LOGGER.info("立即启动记录服务...");
                try {
                    recordQosService.startRecord();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动记录服务，异常原因如下", e);
                }
            } else if (startRecordDelay > 0) {
                LOGGER.info(startRecordDelay + " 毫秒后启动记录服务...");
                scheduler.schedule(
                        () -> {
                            LOGGER.info("启动记录服务...");
                            try {
                                recordQosService.startRecord();
                            } catch (ServiceException e) {
                                LOGGER.error("无法启动记录服务，异常原因如下", e);
                            }
                        },
                        new Date(System.currentTimeMillis() + startRecordDelay)
                );
            }

            // 处理重置处理器的启动选项。
            ResetQosService resetQosService = ctx.getBean(ResetQosService.class);
            // 重置处理器是否启动重置服务。
            long startResetDelay = launcherSettingHandler.getStartResetDelay();
            if (startResetDelay == 0) {
                LOGGER.info("立即启动重置服务...");
                try {
                    resetQosService.start();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动重置服务，异常原因如下", e);
                }
            } else if (startResetDelay > 0) {
                LOGGER.info(startResetDelay + " 毫秒后启动重置服务...");
                scheduler.schedule(
                        () -> {
                            LOGGER.info("启动重置服务...");
                            try {
                                resetQosService.start();
                            } catch (ServiceException e) {
                                LOGGER.error("无法启动重置服务，异常原因如下", e);
                            }
                        },
                        new Date(System.currentTimeMillis() + startResetDelay)
                );
            }
        });
    }
}
