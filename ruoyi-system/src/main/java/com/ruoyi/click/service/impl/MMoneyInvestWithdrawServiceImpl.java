package com.ruoyi.click.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.click.domain.MMoneyInvestWithdraw;
import com.ruoyi.click.mapper.MMoneyInvestWithdrawMapper;
import com.ruoyi.click.service.IMMoneyInvestWithdrawService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 存款取款记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-19
 */
@Service
public class MMoneyInvestWithdrawServiceImpl extends ServiceImpl<MMoneyInvestWithdrawMapper, MMoneyInvestWithdraw> implements IMMoneyInvestWithdrawService
{
    @Autowired
    private MMoneyInvestWithdrawMapper mMoneyInvestWithdrawMapper;

    /**
     * 查询存款取款记录
     *
     * @param id 存款取款记录主键
     * @return 存款取款记录
     */
    @Override
    public MMoneyInvestWithdraw selectMMoneyInvestWithdrawById(Long id)
    {
        return mMoneyInvestWithdrawMapper.selectMMoneyInvestWithdrawById(id);
    }

    /**
     * 查询存款取款记录列表
     *
     * @param mMoneyInvestWithdraw 存款取款记录
     * @return 存款取款记录
     */
    @Override
    public List<MMoneyInvestWithdraw> selectMMoneyInvestWithdrawList(MMoneyInvestWithdraw mMoneyInvestWithdraw)
    {
        return mMoneyInvestWithdrawMapper.selectMMoneyInvestWithdrawList(mMoneyInvestWithdraw);
    }

    /**
     * 新增存款取款记录
     *
     * @param mMoneyInvestWithdraw 存款取款记录
     * @return 结果
     */
    @Override
    public int insertMMoneyInvestWithdraw(MMoneyInvestWithdraw mMoneyInvestWithdraw)
    {
        mMoneyInvestWithdraw.setCreateTime(DateUtils.getNowDate());
        return mMoneyInvestWithdrawMapper.insertMMoneyInvestWithdraw(mMoneyInvestWithdraw);
    }

    /**
     * 修改存款取款记录
     *
     * @param mMoneyInvestWithdraw 存款取款记录
     * @return 结果
     */
    @Override
    public int updateMMoneyInvestWithdraw(MMoneyInvestWithdraw mMoneyInvestWithdraw)
    {
        mMoneyInvestWithdraw.setUpdateTime(DateUtils.getNowDate());
        return mMoneyInvestWithdrawMapper.updateMMoneyInvestWithdraw(mMoneyInvestWithdraw);
    }

    /**
     * 批量删除存款取款记录
     *
     * @param ids 需要删除的存款取款记录主键
     * @return 结果
     */
    @Override
    public int deleteMMoneyInvestWithdrawByIds(Long[] ids)
    {
        return mMoneyInvestWithdrawMapper.deleteMMoneyInvestWithdrawByIds(ids);
    }

    /**
     * 删除存款取款记录信息
     *
     * @param id 存款取款记录主键
     * @return 结果
     */
    @Override
    public int deleteMMoneyInvestWithdrawById(Long id)
    {
        return mMoneyInvestWithdrawMapper.deleteMMoneyInvestWithdrawById(id);
    }


    /**
     * 一个用户一天内的取现或充值数额总计
     * @param param
     * @return 统计数量结果
     */
    @Override
    public BigDecimal sumAmountByUserDateType(Map<String,Object> param){
        return mMoneyInvestWithdrawMapper.sumAmountByUserDateType(param);
    }

    /**
     * 根据用户id修改用户信息
     * @param param
     * @return 修改数量
     */
    @Override
    public int updateUserInfoByUserId(Map<String,Object> param){
        return mMoneyInvestWithdrawMapper.updateUserInfoByUserId(param);
    }

    @Override
    public MMoneyInvestWithdraw getTodayWithdraw(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        return lambdaQuery()
                .eq(MMoneyInvestWithdraw::getUserId, userId)
                .eq(MMoneyInvestWithdraw::getStatus, 1)
                .eq(MMoneyInvestWithdraw::getType, 0)
                .between(MMoneyInvestWithdraw::getCreateTime, startOfDay, endOfDay)
                .last("LIMIT 1")
                .one();
    }
}
