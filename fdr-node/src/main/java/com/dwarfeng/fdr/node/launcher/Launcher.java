package com.dwarfeng.fdr.node.launcher;

import com.dwarfeng.fdr.node.handler.LauncherSettingHandler;
import com.dwarfeng.fdr.stack.service.*;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.4.2
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性重置过滤器。
            mayResetFilter(ctx);

            // 根据启动器设置处理器的设置，选择性重置触发器。
            mayResetTrigger(ctx);

            // 根据启动器设置处理器的设置，选择性重置映射器。
            mayResetMapper(ctx);

            // 根据启动器设置处理器的设置，选择性重置清洗器。
            mayResetWasher(ctx);

            // 根据启动器设置处理器的设置，选择性开启记录服务。
            mayStartRecord(ctx);

            // 根据启动器设置处理器的设置，选择性开启重置服务。
            mayStartReset(ctx);
        });
    }

    private static void mayResetFilter(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        //判断是否重置过滤器。
        if (launcherSettingHandler.isResetFilterSupport()) {
            LOGGER.info("重置过滤器支持...");
            FilterSupportMaintainService maintainService = ctx.getBean(FilterSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("过滤器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayResetTrigger(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        //判断是否重置触发器。
        if (launcherSettingHandler.isResetTriggerSupport()) {
            LOGGER.info("重置触发器支持...");
            TriggerSupportMaintainService maintainService = ctx.getBean(TriggerSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("触发器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayResetMapper(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        //判断是否重置映射器。
        if (launcherSettingHandler.isResetMapperSupport()) {
            LOGGER.info("重置映射器支持...");
            MapperSupportMaintainService maintainService = ctx.getBean(MapperSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("映射器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayResetWasher(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        //判断是否重置映射器。
        if (launcherSettingHandler.isResetWasherSupport()) {
            LOGGER.info("重置清洗器支持...");
            WasherSupportMaintainService maintainService = ctx.getBean(WasherSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("清洗器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayStartRecord(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理记录处理器的启动选项。
        RecordQosService recordQosService = ctx.getBean(RecordQosService.class);

        // 判断是否开启记录服务。
        long startRecordDelay = launcherSettingHandler.getStartRecordDelay();
        if (startRecordDelay == 0) {
            LOGGER.info("立即启动记录服务...");
            try {
                recordQosService.startRecord();
            } catch (ServiceException e) {
                LOGGER.error("无法启动记录服务，异常原因如下", e);
            }
        } else if (startRecordDelay > 0) {
            LOGGER.info("{} 毫秒后启动记录服务...", startRecordDelay);
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
    }

    private static void mayStartReset(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

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
            LOGGER.info("{} 毫秒后启动重置服务...", startResetDelay);
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
    }
}
