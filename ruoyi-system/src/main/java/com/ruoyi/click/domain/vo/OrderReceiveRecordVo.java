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

    /** 利润 */
    private BigDecimal profit;

    /** 退款金额 */
    private BigDecimal refundAmount;

    /** 过程状态 */
    private String processStatus;

    private Integer numTarget;

    private Integer numSeq;

    /** 是否连单 */
    private String multiType;

    /** 连单id */
    private Long multiId;

    /** 冻结状态 */
    private String freezeStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

}
