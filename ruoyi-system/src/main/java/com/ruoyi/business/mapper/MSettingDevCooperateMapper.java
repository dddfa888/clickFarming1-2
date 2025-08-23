package com.ruoyi.business.mapper;

import java.util.List;

import com.ruoyi.business.domain.MSettingDevCooperate;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设置发展合作Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Mapper
public interface MSettingDevCooperateMapper 
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
     * 删除设置发展合作
     * 
     * @param id 设置发展合作主键
     * @return 结果
     */
    public int deleteMSettingDevCooperateById(Long id);

    /**
     * 批量删除设置发展合作
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMSettingDevCooperateByIds(Long[] ids);

    /**
     * 根据语种查询设置公司简介
     *
     * @param language 语种
     * @return 设置公司简介
     */
    MSettingDevCooperate selectByLang(String language);
}
