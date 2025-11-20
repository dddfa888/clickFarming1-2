package com.ruoyi.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.ruoyi.click.domain.vo.UserOrderSetSaveVO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.MUserOrderSetMapper;
import com.ruoyi.business.domain.MUserOrderSet;
import com.ruoyi.business.service.IMUserOrderSetService;

/**
 * 订单设置Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-30
 */
@Service
public class MUserOrderSetServiceImpl implements IMUserOrderSetService 
{
    @Autowired
    private MUserOrderSetMapper mUserOrderSetMapper;

    /**
     * 查询订单设置
     * 
     * @param id 订单设置主键
     * @return 订单设置
     */
    @Override
    public MUserOrderSet selectMUserOrderSetById(Long id)
    {
        return mUserOrderSetMapper.selectMUserOrderSetById(id);
    }

    /**
     * 查询订单设置列表
     * 
     * @param mUserOrderSet 订单设置
     * @return 订单设置
     */
    @Override
    public List<MUserOrderSet> selectMUserOrderSetList(MUserOrderSet mUserOrderSet)
    {
        return mUserOrderSetMapper.selectMUserOrderSetList(mUserOrderSet);
    }

    /**
     * 根据用户id查询订单设置列表
     *
     * @param userId
     * @return 订单设置
     */
    @Override
    public List<MUserOrderSet> selectByUserId(Long userId)
    {
        return mUserOrderSetMapper.selectByUserId(userId);
    }

    /**
     * 新增订单设置
     * 
     * @param mUserOrderSet 订单设置
     * @return 结果
     */
    @Override
    public int insertMUserOrderSet(MUserOrderSet mUserOrderSet)
    {
        mUserOrderSet.setCreateTime(DateUtils.getNowDate());
        return mUserOrderSetMapper.insertMUserOrderSet(mUserOrderSet);
    }

    /**
     * 修改订单设置
     * 
     * @param mUserOrderSet 订单设置
     * @return 结果
     */
    @Override
    public int updateMUserOrderSet(MUserOrderSet mUserOrderSet)
    {
        return mUserOrderSetMapper.updateMUserOrderSet(mUserOrderSet);
    }

    /**
     * 批量删除订单设置
     * 
     * @param ids 需要删除的订单设置主键
     * @return 结果
     */
    @Override
    public int deleteMUserOrderSetByIds(Long[] ids)
    {
        return mUserOrderSetMapper.deleteMUserOrderSetByIds(ids);
    }

    /**
     * 删除订单设置信息
     * 
     * @param id 订单设置主键
     * @return 结果
     */
    @Override
    public int deleteMUserOrderSetById(Long id)
    {
        return mUserOrderSetMapper.deleteMUserOrderSetById(id);
    }


    /**
     * 批量更新一个用户的设置
     *
     * @param vo 订单设置
     * @return 结果
     */
    @Override
    public int saveOrderSetByUser(UserOrderSetSaveVO vo)
    {
        mUserOrderSetMapper.deleteByUserId(vo.getUserId());

        String dataStr = vo.getOrderSetData();
        //Assert.notNull(dataStr, "设置数据为空");
        if(StringUtils.isEmpty(dataStr))
            return 1; //1表示成功，没有报错，返回0表示操作失败

        Date date = DateUtils.getNowDate();
        String[] dataArray = dataStr.split("#");
        List<MUserOrderSet> list = new LinkedList<>();
        MUserOrderSet orderSetset = null;
        for(String rowStr : dataArray){
            String[] rowArray = rowStr.split("_");
            if(rowArray.length<3){
                continue;
            }
            orderSetset = new MUserOrderSet();
            orderSetset.setUserId(vo.getUserId());
            orderSetset.setOrderNum(Integer.valueOf(rowArray[0]));
            orderSetset.setMinNum(new BigDecimal(rowArray[1]));
            orderSetset.setMaxNum(new BigDecimal(rowArray[2]));
            orderSetset.setCreateTime(date);
            list.add(orderSetset);
        }
        return mUserOrderSetMapper.insertBatch(list);
    }
    /**
     * 刷新用户每日任务
     */
//    public void refreshDailyTasks() {
//        System.out.println("=== 开始执行用户每日任务刷新 ===");
//        System.out.println("执行时间: " + new java.util.Date());
//
//        // 添加一些实际的业务逻辑用于测试
//        try {
//            // 示例：查询所有用户的订单设置数量
//            List<MUserOrderSet> allOrderSets = mUserOrderSetMapper.selectMUserOrderSetList(new MUserOrderSet());
//            System.out.println("当前总订单设置数量: " + allOrderSets.size());
//
//            // 可以在这里添加实际的刷新逻辑
//            System.out.println("模拟刷新用户任务完成");
//
//        } catch (Exception e) {
//            System.err.println("刷新任务时发生错误: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        System.out.println("=== 用户每日任务刷新完成 ===");
//    }
}
