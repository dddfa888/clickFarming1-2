package com.ruoyi.web.controller.click.sse;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.click.domain.MMoneyInvestWithdraw;
import com.ruoyi.click.service.IMMoneyInvestWithdrawService;
import com.ruoyi.common.annotation.Anonymous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/sse")
public class OrderTipSseController {

    // 每个客户端一个连接
    @Autowired
    private IMMoneyInvestWithdrawService mMoneyInvestWithdrawService;

    /**
     * 建立 SSE 连接接口
     */
    @Anonymous
    @GetMapping(value = "/connect", produces = "text/event-stream")
    public SseEmitter connect() {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30分钟超时
        AtomicBoolean running = new AtomicBoolean(true);


        try {
            emitter.send(SseEmitter.event().name("CONNECTED").data("连接成功"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        new Thread(() -> {
            int lastValue = 0;
            while (running.get()) {
                try {
                    /*MMoneyInvestWithdraw employeesWithdraw = new MMoneyInvestWithdraw();
                    employeesWithdraw.setStatus(0);
                    employeesWithdraw.setType("0");
                    employeesWithdraw.setUserType("0");
                    int employeesWithdrawCount = mMoneyInvestWithdrawService.selectMMoneyInvestWithdrawList(employeesWithdraw).size();

                    MMoneyInvestWithdraw clientsWithdraw = new MMoneyInvestWithdraw();
                    clientsWithdraw.setStatus(0);
                    clientsWithdraw.setType("0");
                    clientsWithdraw.setUserType("1");
                    int clientsWithdrawWithdrawCount = mMoneyInvestWithdrawService.selectMMoneyInvestWithdrawList(clientsWithdraw).size();*/

                    Wrapper wrapperE = new LambdaQueryWrapper<MMoneyInvestWithdraw>()
                            .eq(MMoneyInvestWithdraw::getStatus, 0)
                            .eq(MMoneyInvestWithdraw::getType, "0")
                            .eq(MMoneyInvestWithdraw::getUserType, "0");
                    long employeesWithdrawCount = mMoneyInvestWithdrawService.count(wrapperE);
                    Wrapper wrapperC = new LambdaQueryWrapper<MMoneyInvestWithdraw>()
                            .eq(MMoneyInvestWithdraw::getStatus, 0)
                            .eq(MMoneyInvestWithdraw::getType, "0")
                            .eq(MMoneyInvestWithdraw::getUserType, "1");
                    long clientsWithdrawWithdrawCount = mMoneyInvestWithdrawService.count(wrapperC);

                    Map<String, Object> fixedData = new HashMap<>();
                    fixedData.put("withdrawEmployee", employeesWithdrawCount);
                    fixedData.put("withdrawCustomer", clientsWithdrawWithdrawCount);
                    emitter.send(SseEmitter.event().name("NOTICE").data(fixedData));

                    Thread.sleep(3000); // 每秒推送一次
                } catch (Exception e) {
                    emitter.completeWithError(e);
                    e.printStackTrace();
                    break;
                }
            }
        }).start();

        return emitter;
    }


}
