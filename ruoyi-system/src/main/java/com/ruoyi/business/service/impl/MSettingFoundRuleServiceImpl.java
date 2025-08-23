package com.ruoyi.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.MSettingFoundRuleMapper;
import com.ruoyi.business.domain.MSettingFoundRule;
import com.ruoyi.business.service.IMSettingFoundRuleService;

/**
 * 设置基本原则Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Service
public class MSettingFoundRuleServiceImpl implements IMSettingFoundRuleService 
{
    @Autowired
    private MSettingFoundRuleMapper mSettingFoundRuleMapper;

    /**
     * 查询设置基本原则
     * 
     * @param id 设置基本原则主键
     * @return 设置基本原则
     */
    @Override
    public MSettingFoundRule selectMSettingFoundRuleById(Long id)
    {
        return mSettingFoundRuleMapper.selectMSettingFoundRuleById(id);
    }

    /**
     * 查询设置基本原则列表
     * 
     * @param mSettingFoundRule 设置基本原则
     * @return 设置基本原则
     */
    @Override
    public List<MSettingFoundRule> selectMSettingFoundRuleList(MSettingFoundRule mSettingFoundRule)
    {
        return mSettingFoundRuleMapper.selectMSettingFoundRuleList(mSettingFoundRule);
    }

    /**
     * 新增设置基本原则
     * 
     * @param mSettingFoundRule 设置基本原则
     * @return 结果
     */
    @Override
    public int insertMSettingFoundRule(MSettingFoundRule mSettingFoundRule)
    {
        mSettingFoundRule.setCreateTime(DateUtils.getNowDate());
        return mSettingFoundRuleMapper.insertMSettingFoundRule(mSettingFoundRule);
    }

    /**
     * 修改设置基本原则
     * 
     * @param mSettingFoundRule 设置基本原则
     * @return 结果
     */
    @Override
    public int updateMSettingFoundRule(MSettingFoundRule mSettingFoundRule)
    {
        mSettingFoundRule.setUpdateTime(DateUtils.getNowDate());
        return mSettingFoundRuleMapper.updateMSettingFoundRule(mSettingFoundRule);
    }

    /**
     * 批量删除设置基本原则
     * 
     * @param ids 需要删除的设置基本原则主键
     * @return 结果
     */
    @Override
    public int deleteMSettingFoundRuleByIds(Long[] ids)
    {
        return mSettingFoundRuleMapper.deleteMSettingFoundRuleByIds(ids);
    }

    /**
     * 删除设置基本原则信息
     * 
     * @param id 设置基本原则主键
     * @return 结果
     */
    @Override
    public int deleteMSettingFoundRuleById(Long id)
    {
        return mSettingFoundRuleMapper.deleteMSettingFoundRuleById(id);
    }

    /**
     * 根据语种查询设置基本原则信息
     *
     * @param lang 语种
     * @return 设置基本原则信息
     */
    @Override
    public MSettingFoundRule selectByLang(String lang)
    {
        if (lang == null
                || lang.trim().isEmpty()
                || !(lang.equals("zh") || lang.equals("vi"))) {
            lang = "en";
        }
        return mSettingFoundRuleMapper.selectByLang(lang);
    }

}
