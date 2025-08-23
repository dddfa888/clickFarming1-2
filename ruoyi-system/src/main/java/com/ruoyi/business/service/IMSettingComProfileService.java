package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.MSettingComProfile;

/**
 * 设置公司简介Service接口
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
public interface IMSettingComProfileService 
{
    /**
     * 查询设置公司简介
     * 
     * @param id 设置公司简介主键
     * @return 设置公司简介
     */
    public MSettingComProfile selectMSettingComProfileById(Long id);

    /**
     * 查询设置公司简介列表
     * 
     * @param mSettingComProfile 设置公司简介
     * @return 设置公司简介集合
     */
    public List<MSettingComProfile> selectMSettingComProfileList(MSettingComProfile mSettingComProfile);

    /**
     * 新增设置公司简介
     * 
     * @param mSettingComProfile 设置公司简介
     * @return 结果
     */
    public int insertMSettingComProfile(MSettingComProfile mSettingComProfile);

    /**
     * 修改设置公司简介
     * 
     * @param mSettingComProfile 设置公司简介
     * @return 结果
     */
    public int updateMSettingComProfile(MSettingComProfile mSettingComProfile);

    /**
     * 批量删除设置公司简介
     * 
     * @param ids 需要删除的设置公司简介主键集合
     * @return 结果
     */
    public int deleteMSettingComProfileByIds(Long[] ids);

    /**
     * 删除设置公司简介信息
     * 
     * @param id 设置公司简介主键
     * @return 结果
     */
    public int deleteMSettingComProfileById(Long id);

    /**
     * 根据语种查询设置公司简介
     *
     * @param lang 语种
     * @return 设置公司简介
     */
    MSettingComProfile selectByLang(String lang);
}
