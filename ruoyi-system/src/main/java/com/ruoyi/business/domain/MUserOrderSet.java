package com.ruoyi.business.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单设置对象 m_user_order_set
 * 
 * @author ruoyi
 * @date 2025-06-30
 */
public class MUserOrderSet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 订单序号 */
    @Excel(name = "订单序号")
    private Integer orderNum;

    /** 最小值 */
    @Excel(name = "最小值")
    private BigDecimal minNum;

    /** 最大值 */
    @Excel(name = "最大值")
    private BigDecimal maxNum;

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

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setMinNum(BigDecimal minNum) 
    {
        this.minNum = minNum;
    }

    public BigDecimal getMinNum() 
    {
        return minNum;
    }

    public void setMaxNum(BigDecimal maxNum) 
    {
        this.maxNum = maxNum;
    }

    public BigDecimal getMaxNum() 
    {
        return maxNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("orderNum", getOrderNum())
            .append("minNum", getMinNum())
            .append("maxNum", getMaxNum())
            .append("createTime", getCreateTime())
            .toString();
    }
}
