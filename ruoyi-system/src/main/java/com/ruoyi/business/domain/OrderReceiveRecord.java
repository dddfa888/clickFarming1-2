package com.ruoyi.business.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单接收记录对象 m_order_receive_record
 *
 * @author ruoyi
 * @date 2025-06-17
 */
public class OrderReceiveRecord extends BaseEntity
{
    public static final String PROCESS_STATUS_WAIT = "Waiting";     //过程状态：未完成
    public static final String PROCESS_STATUS_SUCCESS = "Success"; //过程状态：成功完成
    public static final String MULTI_TYPE_YES = "Y"; //是否连单：是
    public static final String MULTI_TYPE_NO = "N"; //是否连单：否
    public static final String FREEZE_STATUS_YES = "Y"; //冻结状态：是
    public static final String FREEZE_STATUS_NO = "N"; //冻结状态：否

    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 产品id */
    @Excel(name = "产品id")
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品图片URL */
    @Excel(name = "产品图片URL")
    private String productImageUrl;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /** 数量 */
    @Excel(name = "数量")
    private Integer number;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 利润 */
    @Excel(name = "利润")
    private BigDecimal profit;

    /** 退款金额 */
    @Excel(name = "退款金额")
    private BigDecimal refundAmount;

    /** 过程状态 */
    @Excel(name = "过程状态")
    private String processStatus;

    @Excel(name = "订单目标数量")
    private Integer numTarget;

    @Excel(name = "订单序号")
    private Integer numSeq;

    /** 是否连单 */
    @Excel(name = "是否连单")
    private String multiType;

    /** 连单id */
    @Excel(name = "连单id")
    private Long multiId;

    /** 冻结状态 */
    @Excel(name = "冻结状态")
    private String freezeStatus;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductImageUrl(String productImageUrl)
    {
        this.productImageUrl = productImageUrl;
    }

    public String getProductImageUrl()
    {
        return productImageUrl;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    public Integer getNumber()
    {
        return number;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setProfit(BigDecimal profit)
    {
        this.profit = profit;
    }

    public BigDecimal getProfit()
    {
        return profit;
    }

    public void setRefundAmount(BigDecimal refundAmount)
    {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getRefundAmount()
    {
        return refundAmount;
    }

    public void setProcessStatus(String processStatus)
    {
        this.processStatus = processStatus;
    }

    public String getProcessStatus()
    {
        return processStatus;
    }

    public void setNumTarget(Integer numTarget)
    {
        this.numTarget = numTarget;
    }

    public Integer getNumTarget()
    {
        return numTarget;
    }

    public void setNumSeq(Integer numSeq)
    {
        this.numSeq = numSeq;
    }

    public Integer getNumSeq()
    {
        return numSeq;
    }

    public void setMultiType(String multiType)
    {
        this.multiType = multiType;
    }

    public String getMultiType()
    {
        return multiType;
    }

    public void setMultiId(Long multiId)
    {
        this.multiId = multiId;
    }

    public Long getMultiId()
    {
        return multiId;
    }

    public void setFreezeStatus(String freezeStatus)
    {
        this.freezeStatus = freezeStatus;
    }

    public String getFreezeStatus()
    {
        return freezeStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("productImageUrl", getProductImageUrl())
            .append("unitPrice", getUnitPrice())
            .append("number", getNumber())
            .append("totalAmount", getTotalAmount())
            .append("profit", getProfit())
            .append("refundAmount", getRefundAmount())
            .append("processStatus", getProcessStatus())
            .append("multiType", getMultiType())
            .append("multiId", getMultiId())
            .append("freezeStatus", getFreezeStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
