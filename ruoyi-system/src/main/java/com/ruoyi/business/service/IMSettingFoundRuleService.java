package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.MSettingFoundRule;

/**
 * 设置基本原则Service接口
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
public interface IMSettingFoundRuleService 
{
    /**
     * 查询设置基本原则
     * 
     * @param id 设置基本原则主键
     * @return 设置基本原则
     */
    public MSettingFoundRule selectMSettingFoundRuleById(Long id);

    /**
     * 查询设置基本原则列表
     * 
     * @param mSettingFoundRule 设置基本原则
     * @return 设置基本原则集合
     */
    public List<MSettingFoundRule> selectMSettingFoundRuleList(MSettingFoundRule mSettingFoundRule);

    /**
     * 新增设置基本原则
     * 
     * @param mSettingFoundRule 设置基本原则
     * @return 结果
     */
    public int insertMSettingFoundRule(MSettingFoundRule mSettingFoundRule);

    /**
     * 修改设置基本原则
     * 
     * @param mSettingFoundRule 设置基本原则
     * @return 结果
     */
    public int updateMSettingFoundRule(MSettingFoundRule mSettingFoundRule);

    /**
     * 批量删除设置基本原则
     * 
     * @param ids 需要删除的设置基本原则主键集合
     * @return 结果
     */
    public int deleteMSettingFoundRuleByIds(Long[] ids);

    /**
     * 删除设置基本原则信息
     * 
     * @param id 设置基本原则主键
     * @return 结果
     */
    public int deleteMSettingFoundRuleById(Long id);

    /**
     * 根据语种查询设置基本原则信息
     *
     * @param lang 语种
     * @return 设置基本原则信息
     */
    MSettingFoundRule selectByLang(String lang);

}
