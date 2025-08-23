package com.ruoyi.click.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户等级对象 user_grade
 *
 * @author ruoyi
 * @date 2025-06-15
 */
@Data
@TableName("m_user_grade")
public class UserGrade
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 序号 */
    @Excel(name = "序号")
    private Integer sortNum;

    /*  白银  Bạc
        黄金  Vàng
        白金  Bạch Kim
        钻石  Kim Cương  */
    /** 等级名称 */
    @Excel(name = "等级名称")
    private String gradeName;

    /** 参加费 */
    @Excel(name = "参加费")
    private String joinCost;

    /** 最低余额 */
    @Excel(name = "最低余额")
    private BigDecimal minBalance;

    /** 每天购买的产品数量 */
    @Excel(name = "每天购买的产品数量")
    private Integer buyProdNum;

    /** 利润（%） */
    @Excel(name = "最小奖金", readConverterExp = "%=")
    private BigDecimal minBonus;

    /** 最大奖金（%） */
    @Excel(name = "最大奖金", readConverterExp = "%=")
    private BigDecimal maxBonus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
