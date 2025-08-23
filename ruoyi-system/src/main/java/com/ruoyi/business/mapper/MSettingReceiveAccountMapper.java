package com.ruoyi.business.mapper;

import java.util.List;

import com.ruoyi.business.domain.MSettingReceiveAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设置收款账户Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@Mapper
public interface MSettingReceiveAccountMapper 
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
     * 删除设置收款账户
     * 
     * @param id 设置收款账户主键
     * @return 结果
     */
    public int deleteMSettingReceiveAccountById(Long id);

    /**
     * 批量删除设置收款账户
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMSettingReceiveAccountByIds(Long[] ids);

    /**
     * 根据语种查询设置公司简介
     *
     * @param language 语种
     * @return 设置公司简介
     */
    MSettingReceiveAccount selectByLang(String language);
}
