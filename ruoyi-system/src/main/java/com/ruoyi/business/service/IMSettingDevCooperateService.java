package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.MSettingDevCooperate;

/**
 * 设置发展合作Service接口
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
public interface IMSettingDevCooperateService 
{
    /**
     * 查询设置发展合作
     * 
     * @param id 设置发展合作主键
     * @return 设置发展合作
     */
    public MSettingDevCooperate selectMSettingDevCooperateById(Long id);

    /**
     * 查询设置发展合作列表
     * 
     * @param mSettingDevCooperate 设置发展合作
     * @return 设置发展合作集合
     */
    public List<MSettingDevCooperate> selectMSettingDevCooperateList(MSettingDevCooperate mSettingDevCooperate);

    /**
     * 新增设置发展合作
     * 
     * @param mSettingDevCooperate 设置发展合作
     * @return 结果
     */
    public int insertMSettingDevCooperate(MSettingDevCooperate mSettingDevCooperate);

    /**
     * 修改设置发展合作
     * 
     * @param mSettingDevCooperate 设置发展合作
     * @return 结果
     */
    public int updateMSettingDevCooperate(MSettingDevCooperate mSettingDevCooperate);

    /**
     * 批量删除设置发展合作
     * 
     * @param ids 需要删除的设置发展合作主键集合
     * @return 结果
     */
    public int deleteMSettingDevCooperateByIds(Long[] ids);

    /**
     * 删除设置发展合作信息
     * 
     * @param id 设置发展合作主键
     * @return 结果
     */
    public int deleteMSettingDevCooperateById(Long id);

    /**
     * 根据语种查询设置发展合作信息
     *
     * @param lang 语种
     * @return 设置发展合作信息
     */
    MSettingDevCooperate selectByLang(String lang);
}
