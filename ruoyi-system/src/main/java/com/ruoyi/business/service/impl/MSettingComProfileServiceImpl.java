package com.ruoyi.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.MSettingComProfileMapper;
import com.ruoyi.business.domain.MSettingComProfile;
import com.ruoyi.business.service.IMSettingComProfileService;

/**
 * 设置公司简介Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Service
public class MSettingComProfileServiceImpl implements IMSettingComProfileService 
{
    @Autowired
    private MSettingComProfileMapper mSettingComProfileMapper;

    /**
     * 查询设置公司简介
     * 
     * @param id 设置公司简介主键
     * @return 设置公司简介
     */
    @Override
    public MSettingComProfile selectMSettingComProfileById(Long id)
    {
        return mSettingComProfileMapper.selectMSettingComProfileById(id);
    }

    /**
     * 查询设置公司简介列表
     * 
     * @param mSettingComProfile 设置公司简介
     * @return 设置公司简介
     */
    @Override
    public List<MSettingComProfile> selectMSettingComProfileList(MSettingComProfile mSettingComProfile)
    {
        return mSettingComProfileMapper.selectMSettingComProfileList(mSettingComProfile);
    }

    /**
     * 新增设置公司简介
     * 
     * @param mSettingComProfile 设置公司简介
     * @return 结果
     */
    @Override
    public int insertMSettingComProfile(MSettingComProfile mSettingComProfile)
    {
        mSettingComProfile.setCreateTime(DateUtils.getNowDate());
        return mSettingComProfileMapper.insertMSettingComProfile(mSettingComProfile);
    }

    /**
     * 修改设置公司简介
     * 
     * @param mSettingComProfile 设置公司简介
     * @return 结果
     */
    @Override
    public int updateMSettingComProfile(MSettingComProfile mSettingComProfile)
    {
        mSettingComProfile.setUpdateTime(DateUtils.getNowDate());
        return mSettingComProfileMapper.updateMSettingComProfile(mSettingComProfile);
    }

    /**
     * 批量删除设置公司简介
     * 
     * @param ids 需要删除的设置公司简介主键
     * @return 结果
     */
    @Override
    public int deleteMSettingComProfileByIds(Long[] ids)
    {
        return mSettingComProfileMapper.deleteMSettingComProfileByIds(ids);
    }

    /**
     * 删除设置公司简介信息
     * 
     * @param id 设置公司简介主键
     * @return 结果
     */
    @Override
    public int deleteMSettingComProfileById(Long id)
    {
        return mSettingComProfileMapper.deleteMSettingComProfileById(id);
    }


    /**
     * 根据语种查询设置公司简介
     *
     * @param lang 语种
     * @return 设置公司简介
     */
    @Override
    public MSettingComProfile selectByLang(String lang)
    {
        Assert.notEmpty(lang, "请选择语种");
        return mSettingComProfileMapper.selectByLang(lang);
    }

}
