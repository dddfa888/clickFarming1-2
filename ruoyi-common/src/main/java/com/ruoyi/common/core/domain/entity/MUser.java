package com.ruoyi.common.core.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

/**
 * 用户对象 m_user
 *
 * @author ruoyi
 * @date 2025-06-15
 */
@Data
public class MUser
{
    private static final long serialVersionUID = 1L;

    /** uid */
    @TableId
    private Long uid;


    /** 头像 */
    @Excel(name = "头像")
    private String headImg;

    /** 等级 */
    @Excel(name = "等级")
    private Integer level;

    /** 账号 */
    @Excel(name = "账号")
    private String loginAccount;

    /** 登录密码 */
    @Excel(name = "登录密码")
    private String loginPassword;

    /** 资金密码 */
    @Excel(name = "资金密码")
    private String fundPassword;

    /** 提现地址 */
    @Excel(name = "提现地址")
    private String withdrawalAddress;

    /** 类型：员工、客人 */
    @Excel(name = "类型：员工、客人")
    private String registerType;

    /** 电话号码 */
    @Excel(name = "电话号码")
    private String phoneNumber;

    /** 电话号码真实性 */
    @Excel(name = "电话号码真实性")
    private String phoneNumberType;

    /** 账户余额 */
    @Excel(name = "账户余额")
    private BigDecimal accountBalance;

    /** 邀请码 */
    @Excel(name = "邀请码")
    private String invitationCode;

    /** 邀请人uid */
    @Excel(name = "邀请人uid")
    private String inviter;

    /** 邀请人代码 */
    @Excel(name = "邀请人代码")
    private String inviterCode;

    /** 邀请人姓名 */
    @Excel(name = "邀请人姓名")
    private String inviterName;

    /** 状态 1:启用 0:禁用 */
    @Excel(name = "状态 1:启用 0:禁用")
    private Integer status;

    /** 银行名称 */
    @Excel(name = "银行名称")
    private String bankName;

    /** 银行账户名称 */
    @Excel(name = "银行账户名称")
    private String bankAccountName;

    /** 银行账号 */
    @Excel(name = "银行账号")
    private String bankAccountNumber;

    /** 上级id(一共四层) */
    @Excel(name = "上级id(一共四层)")
    private String higherUid;

    /** 最后登录IP */
    @Excel(name = "最后登录IP")
    private String lastLoginIp;

    /** 最后登录IP地点 */
    @Excel(name = "最后登录IP地点")
    private String lastLoginIpAddress;


    /** 当天刷单数量 */
    @Excel(name = "当天刷单数量")
    private Integer brushNumber;

    @Excel(name = "连单数量")
    private Integer multiOrderNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @TableField(exist = false)
    private String levelName;

    @TableField(exist = false)
    private Integer hierarchy;

}
