package com.ruoyi.business.mapper;

import java.util.List;

import com.ruoyi.business.domain.MSettingOrderRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设置订购规则Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Mapper
public interface MSettingOrderRuleMapper 
{
    /**
     * 查询设置订购规则
     * 
     * @param id 设置订购规则主键
     * @return 设置订购规则
     */
    public MSettingOrderRule selectMSettingOrderRuleById(Long id);

    /**
     * 查询设置订购规则列表
     * 
     * @param mSettingOrderRule 设置订购规则
     * @return 设置订购规则集合
     */
    public List<MSettingOrderRule> selectMSettingOrderRuleList(MSettingOrderRule mSettingOrderRule);

    /**
     * 新增设置订购规则
     * 
     * @param mSettingOrderRule 设置订购规则
     * @return 结果
     */
    public int insertMSettingOrderRule(MSettingOrderRule mSettingOrderRule);

    /**
     * 修改设置订购规则
     * 
     * @param mSettingOrderRule 设置订购规则
     * @return 结果
     */
    public int updateMSettingOrderRule(MSettingOrderRule mSettingOrderRule);

    /**
     * 删除设置订购规则
     * 
     * @param id 设置订购规则主键
     * @return 结果
     */
    public int deleteMSettingOrderRuleById(Long id);

    /**
     * 批量删除设置订购规则
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMSettingOrderRuleByIds(Long[] ids);

    /**
     * 根据语种查询设置公司简介
     *
     * @param language 语种
     * @return 设置公司简介
     */
    MSettingOrderRule selectByLang(String language);
}
