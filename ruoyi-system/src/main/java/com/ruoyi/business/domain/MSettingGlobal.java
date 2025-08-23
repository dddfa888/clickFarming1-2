package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设置全球化对象 m_setting_global
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
public class MSettingGlobal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产品ID */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String titleName;

    /** 币种 */
    @Excel(name = "币种")
    private String currencyType;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 图片标识url */
    @Excel(name = "图片标识url")
    private String iconImageUrl;

    /** 链接支持 */
    @Excel(name = "链接支持")
    private String linkSupport;

    /** 语言 */
    @Excel(name = "语言")
    private String language;

    /** 弹出图片url */
    @Excel(name = "弹出图片url")
    private String ejectImageUrl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setTitleName(String titleName) 
    {
        this.titleName = titleName;
    }

    public String getTitleName() 
    {
        return titleName;
    }

    public void setCurrencyType(String currencyType) 
    {
        this.currencyType = currencyType;
    }

    public String getCurrencyType() 
    {
        return currencyType;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setIconImageUrl(String iconImageUrl) 
    {
        this.iconImageUrl = iconImageUrl;
    }

    public String getIconImageUrl() 
    {
        return iconImageUrl;
    }

    public void setLinkSupport(String linkSupport) 
    {
        this.linkSupport = linkSupport;
    }

    public String getLinkSupport() 
    {
        return linkSupport;
    }

    public void setLanguage(String language) 
    {
        this.language = language;
    }

    public String getLanguage() 
    {
        return language;
    }

    public void setEjectImageUrl(String ejectImageUrl) 
    {
        this.ejectImageUrl = ejectImageUrl;
    }

    public String getEjectImageUrl() 
    {
        return ejectImageUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("titleName", getTitleName())
            .append("currencyType", getCurrencyType())
            .append("content", getContent())
            .append("iconImageUrl", getIconImageUrl())
            .append("linkSupport", getLinkSupport())
            .append("language", getLanguage())
            .append("ejectImageUrl", getEjectImageUrl())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
