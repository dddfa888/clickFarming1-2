package com.ruoyi.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.MSettingOrderRuleMapper;
import com.ruoyi.business.domain.MSettingOrderRule;
import com.ruoyi.business.service.IMSettingOrderRuleService;

/**
 * 设置订购规则Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Service
public class MSettingOrderRuleServiceImpl implements IMSettingOrderRuleService 
{
    @Autowired
    private MSettingOrderRuleMapper mSettingOrderRuleMapper;

    /**
     * 查询设置订购规则
     * 
     * @param id 设置订购规则主键
     * @return 设置订购规则
     */
    @Override
    public MSettingOrderRule selectMSettingOrderRuleById(Long id)
    {
        return mSettingOrderRuleMapper.selectMSettingOrderRuleById(id);
    }

    /**
     * 查询设置订购规则列表
     * 
     * @param mSettingOrderRule 设置订购规则
     * @return 设置订购规则
     */
    @Override
    public List<MSettingOrderRule> selectMSettingOrderRuleList(MSettingOrderRule mSettingOrderRule)
    {
        return mSettingOrderRuleMapper.selectMSettingOrderRuleList(mSettingOrderRule);
    }

    /**
     * 新增设置订购规则
     * 
     * @param mSettingOrderRule 设置订购规则
     * @return 结果
     */
    @Override
    public int insertMSettingOrderRule(MSettingOrderRule mSettingOrderRule)
    {
        mSettingOrderRule.setCreateTime(DateUtils.getNowDate());
        return mSettingOrderRuleMapper.insertMSettingOrderRule(mSettingOrderRule);
    }

    /**
     * 修改设置订购规则
     * 
     * @param mSettingOrderRule 设置订购规则
     * @return 结果
     */
    @Override
    public int updateMSettingOrderRule(MSettingOrderRule mSettingOrderRule)
    {
        mSettingOrderRule.setUpdateTime(DateUtils.getNowDate());
        return mSettingOrderRuleMapper.updateMSettingOrderRule(mSettingOrderRule);
    }

    /**
     * 批量删除设置订购规则
     * 
     * @param ids 需要删除的设置订购规则主键
     * @return 结果
     */
    @Override
    public int deleteMSettingOrderRuleByIds(Long[] ids)
    {
        return mSettingOrderRuleMapper.deleteMSettingOrderRuleByIds(ids);
    }

    /**
     * 删除设置订购规则信息
     * 
     * @param id 设置订购规则主键
     * @return 结果
     */
    @Override
    public int deleteMSettingOrderRuleById(Long id)
    {
        return mSettingOrderRuleMapper.deleteMSettingOrderRuleById(id);
    }

    /**
     * 根据语种查询设置订购规则主键
     *
     * @param lang 语种
     * @return 设置基本原则信息
     */
    @Override
    public MSettingOrderRule selectByLang(String lang)
    {
        Assert.notEmpty(lang, "请选择语种");
        return mSettingOrderRuleMapper.selectByLang(lang);
    }

}
