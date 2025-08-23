package com.ruoyi.business.service;

import java.util.List;

import com.ruoyi.business.domain.MSettingReceiveAccount;

/**
 * 设置收款账户Service接口
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
public interface IMSettingReceiveAccountService 
{
    /**
     * 查询设置收款账户
     * 
     * @param id 设置收款账户主键
     * @return 设置收款账户
     */
    public MSettingReceiveAccount selectMSettingReceiveAccountById(Long id);

    /**
     * 查询设置收款账户列表
     * 
     * @param mSettingReceiveAccount 设置收款账户
     * @return 设置收款账户集合
     */
    public List<MSettingReceiveAccount> selectMSettingReceiveAccountList(MSettingReceiveAccount mSettingReceiveAccount);

    /**
     * 新增设置收款账户
     * 
     * @param mSettingReceiveAccount 设置收款账户
     * @return 结果
     */
    public int insertMSettingReceiveAccount(MSettingReceiveAccount mSettingReceiveAccount);

    /**
     * 修改设置收款账户
     * 
     * @param mSettingReceiveAccount 设置收款账户
     * @return 结果
     */
    public int updateMSettingReceiveAccount(MSettingReceiveAccount mSettingReceiveAccount);

    /**
     * 批量删除设置收款账户
     * 
     * @param ids 需要删除的设置收款账户主键集合
     * @return 结果
     */
    public int deleteMSettingReceiveAccountByIds(Long[] ids);

    /**
     * 删除设置收款账户信息
     * 
     * @param id 设置收款账户主键
     * @return 结果
     */
    public int deleteMSettingReceiveAccountById(Long id);

    /**
     * 根据语种查询设置收款账户
     *
     * @param lang 语种
     * @return 设置收款账户
     */
    MSettingReceiveAccount selectByLang(String lang);

}
