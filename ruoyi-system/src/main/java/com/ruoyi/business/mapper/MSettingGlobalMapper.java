package com.ruoyi.business.mapper;

import java.util.List;

import com.ruoyi.business.domain.MSettingGlobal;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设置全球化Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Mapper
public interface MSettingGlobalMapper 
{
    /**
     * 查询设置全球化
     * 
     * @param id 设置全球化主键
     * @return 设置全球化
     */
    public MSettingGlobal selectMSettingGlobalById(Long id);

    /**
     * 查询设置全球化列表
     * 
     * @param mSettingGlobal 设置全球化
     * @return 设置全球化集合
     */
    public List<MSettingGlobal> selectMSettingGlobalList(MSettingGlobal mSettingGlobal);

    /**
     * 新增设置全球化
     * 
     * @param mSettingGlobal 设置全球化
     * @return 结果
     */
    public int insertMSettingGlobal(MSettingGlobal mSettingGlobal);

    /**
     * 修改设置全球化
     * 
     * @param mSettingGlobal 设置全球化
     * @return 结果
     */
    public int updateMSettingGlobal(MSettingGlobal mSettingGlobal);

    /**
     * 删除设置全球化
     * 
     * @param id 设置全球化主键
     * @return 结果
     */
    public int deleteMSettingGlobalById(Long id);

    /**
     * 批量删除设置全球化
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMSettingGlobalByIds(Long[] ids);

    /**
     * 根据语种查询设置公司简介
     *
     * @param language 语种
     * @return 设置公司简介
     */
    MSettingGlobal selectByLang(String language);
}
