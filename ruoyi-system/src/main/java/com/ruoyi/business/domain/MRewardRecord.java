package com.ruoyi.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 奖励记录对象 m_reward_record
 * 
 * @author ruoyi
 * @date 2025-06-28
 */
public class MRewardRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 奖励日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "奖励日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rewardTime;

    /** 奖励金额 */
    @Excel(name = "奖励金额")
    private BigDecimal rewardAmount;

    /** 原本余额 */
    @Excel(name = "原本余额")
    private BigDecimal balanceBefore;

    /** 奖励后余额 */
    @Excel(name = "奖励后余额")
    private BigDecimal balanceAfter;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

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

    public void setRewardTime(Date rewardTime) 
    {
        this.rewardTime = rewardTime;
    }

    public Date getRewardTime() 
    {
        return rewardTime;
    }

    public void setRewardAmount(BigDecimal rewardAmount)
    {
        this.rewardAmount = rewardAmount;
    }

    public BigDecimal getRewardAmount()
    {
        return rewardAmount;
    }

    public void setBalanceBefore(BigDecimal balanceBefore)
    {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceBefore()
    {
        return balanceBefore;
    }

    public void setBalanceAfter(BigDecimal balanceAfter)
    {
        this.balanceAfter = balanceAfter;
    }

    public BigDecimal getBalanceAfter()
    {
        return balanceAfter;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("listId", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("rewardTime", getRewardTime())
            .append("rewardAmount", getRewardAmount())
            .append("balanceBefore", getBalanceBefore())
            .append("balanceAfter", getBalanceAfter())
            .append("description", getDescription())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
