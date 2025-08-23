package com.ruoyi.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.MSettingReceiveAccountMapper;
import com.ruoyi.business.domain.MSettingReceiveAccount;
import com.ruoyi.business.service.IMSettingReceiveAccountService;

/**
 * 设置收款账户Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Service
public class MSettingReceiveAccountServiceImpl implements IMSettingReceiveAccountService 
{
    @Autowired
    private MSettingReceiveAccountMapper mSettingReceiveAccountMapper;

    /**
     * 查询设置收款账户
     * 
     * @param id 设置收款账户主键
     * @return 设置收款账户
     */
    @Override
    public MSettingReceiveAccount selectMSettingReceiveAccountById(Long id)
    {
        return mSettingReceiveAccountMapper.selectMSettingReceiveAccountById(id);
    }

    /**
     * 查询设置收款账户列表
     * 
     * @param mSettingReceiveAccount 设置收款账户
     * @return 设置收款账户
     */
    @Override
    public List<MSettingReceiveAccount> selectMSettingReceiveAccountList(MSettingReceiveAccount mSettingReceiveAccount)
    {
        return mSettingReceiveAccountMapper.selectMSettingReceiveAccountList(mSettingReceiveAccount);
    }

    /**
     * 新增设置收款账户
     * 
     * @param mSettingReceiveAccount 设置收款账户
     * @return 结果
     */
    @Override
    public int insertMSettingReceiveAccount(MSettingReceiveAccount mSettingReceiveAccount)
    {
        mSettingReceiveAccount.setCreateTime(DateUtils.getNowDate());
        return mSettingReceiveAccountMapper.insertMSettingReceiveAccount(mSettingReceiveAccount);
    }

    /**
     * 修改设置收款账户
     * 
     * @param mSettingReceiveAccount 设置收款账户
     * @return 结果
     */
    @Override
    public int updateMSettingReceiveAccount(MSettingReceiveAccount mSettingReceiveAccount)
    {
        mSettingReceiveAccount.setUpdateTime(DateUtils.getNowDate());
        return mSettingReceiveAccountMapper.updateMSettingReceiveAccount(mSettingReceiveAccount);
    }

    /**
     * 批量删除设置收款账户
     * 
     * @param ids 需要删除的设置收款账户主键
     * @return 结果
     */
    @Override
    public int deleteMSettingReceiveAccountByIds(Long[] ids)
    {
        return mSettingReceiveAccountMapper.deleteMSettingReceiveAccountByIds(ids);
    }

    /**
     * 删除设置收款账户信息
     * 
     * @param id 设置收款账户主键
     * @return 结果
     */
    @Override
    public int deleteMSettingReceiveAccountById(Long id)
    {
        return mSettingReceiveAccountMapper.deleteMSettingReceiveAccountById(id);
    }


    /**
     * 根据语种查询设置收款账户
     *
     * @param lang 语种
     * @return 设置收款账户
     */
    @Override
    public MSettingReceiveAccount selectByLang(String lang)
    {
        Assert.notEmpty(lang, "请选择语种");
        return mSettingReceiveAccountMapper.selectByLang(lang);
    }

}
