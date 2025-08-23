package com.ruoyi.click.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 存款取款记录对象 m_money_invest_withdraw
 *
 * @author ruoyi
 * @date 2025-06-19
 */
@Data
public class MMoneyInvestWithdraw
{
    private static final long serialVersionUID = 1L;

    /** 变化ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    @Excel(name = "用户类型")
    private String userType;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 银行名称 */
    @Excel(name = "银行名称")
    private String bankName;

    /** 银行账户名称 */
    @Excel(name = "银行账户名称")
    private String bankAccountName;

    /** 银行账号 */
    @Excel(name = "银行账号")
    private String bankAccountNumber;

    /** 类型：0 提现   1 充值 */
    @Excel(name = "类型：0 提现   1 充值")
    private String type;

    /** 驳回原因 */
    @Excel(name = "驳回原因")
    private String reasonForRejection;

    /** 状态：0 审核中   1成功   2驳回    */
    @Excel(name = "状态：0 审核中   1成功   2驳回   ")
    private Integer status;

    /** 订单id */
    @Excel(name = "订单id")
    private String orderId;


    @JsonFormat(pattern = "HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private Date createTime;


    //账变前
    private BigDecimal accountForward;


    //账变后
    private BigDecimal accountBack;

   /* @TableField(exist = false)
    private String registerType;*/
}
