package com.ruoyi.click.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单接收记录对象 m_order_receive_record
 *
 * @author ruoyi
 * @date 2025-06-17
 */
@Data
public class OrderReceiveRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 用户姓名 */
    private String userName;

    /** 产品id */
    private Long productId;

    /** 产品名称 */
    private String productName;

    /** 产品图片URL */
    private String productImageUrl;

    /** 单价 */
    private BigDecimal unitPrice;

    /** 数量 */
    private Integer number;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 利润（原始数值，用于计算） */
    private BigDecimal profit;

    /** 退款金额（原始数值，用于计算） */
    private BigDecimal refundAmount;

    /** 过程状态 */
    private String processStatus;

    /** 订单目标数量 */
    private Integer numTarget;

    /** 订单序号 */
    private Integer numSeq;

    /** 是否连单（Y-是，N-否） */
    private String multiType;

    /** 连单id */
    private Long multiId;

    /** 冻结状态（Y-是，N-否） */
    private String freezeStatus;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // 新增格式化字段（用于前端展示，小数点替换为逗号）
    /** 格式化后的利润（如：39,89） */
    private String formattedProfit;

    /** 格式化后的退款金额（如：10185,69） */
    private String formattedRefundAmount;

    // 新增过程状态和连单状态的文本描述（可选，用于前端直接展示中文）
    /** 过程状态文本描述（如："未完成"、"成功完成"） */
    private String processStatusDesc;

    /** 连单状态文本描述（如："是"、"否"） */
    private String multiTypeDesc;

    /** 冻结状态文本描述（如："是"、"否"） */
    private String freezeStatusDesc;

}
