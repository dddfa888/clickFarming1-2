package com.ruoyi.click.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

/**
 * 账变记录对象 m_account_change_records
 *
 * @author ruoyi
 * @date 2025-06-16
 */
@Data
public class MAccountChangeRecords {
    private static final long serialVersionUID = 1L;

    /** uuid */
    private String uuid;

    /** 用户uid */
    @Excel(name = "用户uid")
    private String uid;

    /** 类型 0收入 1支出 */
    @Excel(name = "类型 0收入 1支出")
    private Integer type;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 订单说明 */
    @Excel(name = "订单说明")
    private Integer transactionType;

    /** 关联id 比如 如果transaction_type为1 则此处应该存 服务器订单的id(m_service_order) */
    @Excel(name = "关联id")
    private String relatedId;

    /** 账变前 */
    @Excel(name = "账变前")
    private BigDecimal accountForward;

    /** 账变后 */
    @Excel(name = "账变后")
    private BigDecimal accountBack;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private String loginAccount;

    @TableField(exist = false)
    private String userName;
}
