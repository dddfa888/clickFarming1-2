package com.ruoyi.click.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.click.domain.MMoneyInvestWithdraw;
import com.ruoyi.click.domain.UserGrade;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 存款取款记录Mapper接口
 *
 * @author ruoyi
 * @date 2025-06-19
 */
@Mapper
public interface MMoneyInvestWithdrawMapper extends BaseMapper<MMoneyInvestWithdraw>
{
    /**
     * 查询存款取款记录
     *
     * @param id 存款取款记录主键
     * @return 存款取款记录
     */
    MMoneyInvestWithdraw selectMMoneyInvestWithdrawById(Long id);

    /**
     * 查询存款取款记录列表
     *
     * @param mMoneyInvestWithdraw 存款取款记录
     * @return 存款取款记录集合
     */
    List<MMoneyInvestWithdraw> selectMMoneyInvestWithdrawList(MMoneyInvestWithdraw mMoneyInvestWithdraw);

    /**
     * 新增存款取款记录
     *
     * @param mMoneyInvestWithdraw 存款取款记录
     * @return 结果
     */
    int insertMMoneyInvestWithdraw(MMoneyInvestWithdraw mMoneyInvestWithdraw);

    /**
     * 修改存款取款记录
     *
     * @param mMoneyInvestWithdraw 存款取款记录
     * @return 结果
     */
    int updateMMoneyInvestWithdraw(MMoneyInvestWithdraw mMoneyInvestWithdraw);

    /**
     * 删除存款取款记录
     *
     * @param id 存款取款记录主键
     * @return 结果
     */
    int deleteMMoneyInvestWithdrawById(Long id);

    /**
     * 批量删除存款取款记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMMoneyInvestWithdrawByIds(Long[] ids);

    /**
     * 一个用户一天内的取现或充值数额总计
     * @param param
     * @return 统计数量结果
     */
    BigDecimal sumAmountByUserDateType(Map<String,Object> param);

    /**
     * 根据用户id修改用户信息
     *
     * @param param
     * @return 结果
     */
    int updateUserInfoByUserId(Map<String,Object> param);
}
