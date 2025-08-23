package com.ruoyi.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.MSettingGlobalMapper;
import com.ruoyi.business.domain.MSettingGlobal;
import com.ruoyi.business.service.IMSettingGlobalService;

/**
 * 设置全球化Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Service
public class MSettingGlobalServiceImpl implements IMSettingGlobalService 
{
    @Autowired
    private MSettingGlobalMapper mSettingGlobalMapper;

    /**
     * 查询设置全球化
     * 
     * @param id 设置全球化主键
     * @return 设置全球化
     */
    @Override
    public MSettingGlobal selectMSettingGlobalById(Long id)
    {
        return mSettingGlobalMapper.selectMSettingGlobalById(id);
    }

    /**
     * 查询设置全球化列表
     * 
     * @param mSettingGlobal 设置全球化
     * @return 设置全球化
     */
    @Override
    public List<MSettingGlobal> selectMSettingGlobalList(MSettingGlobal mSettingGlobal)
    {
        return mSettingGlobalMapper.selectMSettingGlobalList(mSettingGlobal);
    }

    /**
     * 新增设置全球化
     * 
     * @param mSettingGlobal 设置全球化
     * @return 结果
     */
    @Override
    public int insertMSettingGlobal(MSettingGlobal mSettingGlobal)
    {
        mSettingGlobal.setCreateTime(DateUtils.getNowDate());
        return mSettingGlobalMapper.insertMSettingGlobal(mSettingGlobal);
    }

    /**
     * 修改设置全球化
     * 
     * @param mSettingGlobal 设置全球化
     * @return 结果
     */
    @Override
    public int updateMSettingGlobal(MSettingGlobal mSettingGlobal)
    {
        mSettingGlobal.setUpdateTime(DateUtils.getNowDate());
        return mSettingGlobalMapper.updateMSettingGlobal(mSettingGlobal);
    }

    /**
     * 批量删除设置全球化
     * 
     * @param ids 需要删除的设置全球化主键
     * @return 结果
     */
    @Override
    public int deleteMSettingGlobalByIds(Long[] ids)
    {
        return mSettingGlobalMapper.deleteMSettingGlobalByIds(ids);
    }

    /**
     * 删除设置全球化信息
     * 
     * @param id 设置全球化主键
     * @return 结果
     */
    @Override
    public int deleteMSettingGlobalById(Long id)
    {
        return mSettingGlobalMapper.deleteMSettingGlobalById(id);
    }

    /**
     * 根据语种查询设置全球化
     *
     * @param lang 语种
     * @return 设置全球化
     */
    @Override
    public MSettingGlobal selectByLang(String lang)
    {
        Assert.notEmpty(lang, "请选择语种");
        return mSettingGlobalMapper.selectByLang(lang);
    }

}
