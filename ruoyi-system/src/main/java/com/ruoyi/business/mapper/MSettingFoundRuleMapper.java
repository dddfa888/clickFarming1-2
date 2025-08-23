package com.ruoyi.business.mapper;

import java.util.List;

import com.ruoyi.business.domain.MSettingFoundRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设置基本原则Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Mapper
public interface MSettingFoundRuleMapper 
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
     * 删除设置基本原则
     * 
     * @param id 设置基本原则主键
     * @return 结果
     */
    public int deleteMSettingFoundRuleById(Long id);

    /**
     * 批量删除设置基本原则
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMSettingFoundRuleByIds(Long[] ids);

    /**
     * 根据语种查询设置公司简介
     *
     * @param language 语种
     * @return 设置公司简介
     */
    MSettingFoundRule selectByLang(String language);
}
