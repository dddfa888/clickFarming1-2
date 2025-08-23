package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.MUserOrderSet;
import org.apache.ibatis.annotations.Mapper;
/**
 * 订单设置Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-30
 */
@Mapper
public interface MUserOrderSetMapper 
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
     * @return 订单设置集合
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
     * 批量新增订单设置
     *
     * @param list 《订单设置》列表
     * @return 结果
     */
    int insertBatch(List<MUserOrderSet> list);

    /**
     * 修改订单设置
     * 
     * @param mUserOrderSet 订单设置
     * @return 结果
     */
    int updateMUserOrderSet(MUserOrderSet mUserOrderSet);

    /**
     * 删除订单设置
     * 
     * @param id 订单设置主键
     * @return 结果
     */
    int deleteMUserOrderSetById(Long id);

    /**
     * 删除一个用户的订单设置
     *
     * @param userId 用户id
     * @return 结果
     */
    int deleteByUserId(Long userId);

    /**
     * 批量删除订单设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMUserOrderSetByIds(Long[] ids);
}
