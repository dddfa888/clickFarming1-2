package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.MUserOrderSet;
import com.ruoyi.click.domain.vo.UserOrderSetSaveVO;

/**
 * 订单设置Service接口
 * 
 * @author ruoyi
 * @date 2025-06-30
 */
public interface IMUserOrderSetService 
{
    /**
     * 查询订单设置
     * 
     * @param id 订单设置主键
     * @return 订单设置
     */
    MUserOrderSet selectMUserOrderSetById(Long id);

    /**
     * 查询订单设置列表
     * 
     * @param mUserOrderSet 订单设置
     * @return 订单设置集合
     */
    List<MUserOrderSet> selectMUserOrderSetList(MUserOrderSet mUserOrderSet);

    /**
     * 根据用户id查询订单设置列表
     *
     * @param userId
     * @return 订单设置
     */
    List<MUserOrderSet> selectByUserId(Long userId);

    /**
     * 新增订单设置
     * 
     * @param mUserOrderSet 订单设置
     * @return 结果
     */
    int insertMUserOrderSet(MUserOrderSet mUserOrderSet);

    /**
     * 修改订单设置
     * 
     * @param mUserOrderSet 订单设置
     * @return 结果
     */
    int updateMUserOrderSet(MUserOrderSet mUserOrderSet);

    /**
     * 批量删除订单设置
     * 
     * @param ids 需要删除的订单设置主键集合
     * @return 结果
     */
    int deleteMUserOrderSetByIds(Long[] ids);

    /**
     * 删除订单设置信息
     * 
     * @param id 订单设置主键
     * @return 结果
     */
    int deleteMUserOrderSetById(Long id);

    /**
     * 批量更新一个用户的设置
     *
     * @param vo 订单设置
     * @return 结果
     */
    int saveOrderSetByUser(UserOrderSetSaveVO vo);
}
