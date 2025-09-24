//package com.ruoyi.system.service.impl;
//
//import com.ruoyi.business.service.IMUserOrderSetService;
//import com.ruoyi.business.service.impl.MUserOrderSetServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//
///**
// * 任务刷新服务 - 按越南时间每天凌晨2点刷新
// */
//@Service
//public class TaskRefreshServiceImpl {
//
//    @Autowired
//    private IMUserOrderSetService mUserOrderSetService;
//
//    // 记录上次更新时间，避免重复执行
//    private volatile LocalDateTime lastRefreshTime = null;
//
//    /**
//     * 每小时检查一次是否到达越南时间凌晨2点
//     */
//    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Ho_Chi_Minh")
//    public void checkAndRefreshDailyTasks() {
//        // 测试时直接执行，不检查时间条件
//        System.out.println("定时任务触发 - 当前时间: " + LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
//        performDailyTaskRefresh();
//
//        LocalDateTime currentVietnamTime = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
//
//        // 检查当前是否为凌晨2点
//        if (currentVietnamTime.getHour() == 2) {
//            // 检查今天是否已经执行过刷新
//            if (lastRefreshTime == null ||
//                    !lastRefreshTime.toLocalDate().equals(currentVietnamTime.toLocalDate())) {
//
//                performDailyTaskRefresh();
//                lastRefreshTime = currentVietnamTime;
//            }
//        }
//    }
//
//    /**
//     * 执行每日任务刷新
//     */
//    private void performDailyTaskRefresh() {
//        System.out.println("开始执行每日任务刷新 - 越南时间: " +
//                LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toString());
//
//        try {
//            // 调用具体的业务逻辑
//            if (mUserOrderSetService instanceof MUserOrderSetServiceImpl) {
//                ((MUserOrderSetServiceImpl) mUserOrderSetService).refreshDailyTasks();
//            }
//
//            System.out.println("每日任务刷新完成");
//        } catch (Exception e) {
//            System.err.println("每日任务刷新失败: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
