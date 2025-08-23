package com.ruoyi.business.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.business.domain.OrderReceiveRecord;
import com.ruoyi.click.domain.vo.OrderReceiveRecordVo;

/**
 * 订单接收记录Service接口
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public interface IOrderReceiveRecordService 
{
    /**
     * 查询订单接收记录
     * 
     * @param id 订单接收记录主键
     * @return 订单接收记录
     */
    OrderReceiveRecord selectOrderReceiveRecordById(Long id);

    /**
     * 查询订单接收记录列表
     * 
     * @param orderReceiveRecord 订单接收记录
     * @return 订单接收记录集合
     */
    List<OrderReceiveRecord> selectOrderReceiveRecordList(OrderReceiveRecord orderReceiveRecord);

    /**
     * 倒序查询一个用户订单接收记录列表
     *
     * @param orderReceiveRecord 订单接收记录
     * @return 订单接收记录集合
     */
    List<OrderReceiveRecordVo> selectOrderListByUser(OrderReceiveRecord orderReceiveRecord);

    /**
     * 新增订单接收记录
     * 
     * @param orderReceiveRecord 订单接收记录
     * @return 结果
     */
    int insertOrderReceiveRecord(OrderReceiveRecord orderReceiveRecord);

    /**
     * 修改订单接收记录
     * 
     * @param orderReceiveRecord 订单接收记录
     * @return 结果
     */
    int updateOrderReceiveRecord(OrderReceiveRecord orderReceiveRecord);

    /**
     * 批量删除订单接收记录
     * 
     * @param ids 需要删除的订单接收记录主键集合
     * @return 结果
     */
    int deleteOrderReceiveRecordByIds(Long[] ids);

    /**
     * 删除订单接收记录信息
     * 
     * @param id 订单接收记录主键
     * @return 结果
     */
    int deleteOrderReceiveRecordById(Long id);

    /**
     * 统计一个用户当日的订单数量
     *
     * @param
     * @return 结果
     */
    int countNumByUserDate();

    /**
     * 前台用户点击后添加订单
     * @return
     */
    Map<String,Object> insertOrderByUser(OrderReceiveRecord orderReceiveRecord);


    /**
     * 支付订单
     * @param orderId
     */
    int payOrder(Long orderId);

}
