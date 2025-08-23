package com.ruoyi.click.service;

import com.ruoyi.click.domain.MAccountChangeRecords;

import java.util.List;
import java.util.Map;

/**
 * 账变记录Service接口
 *
 * @author ruoyi
 * @date 2025-06-16
 */
public interface IMAccountChangeRecordsService
{
    /**
     * 查询账变记录
     *
     * @param uuid 账变记录主键
     * @return 账变记录
     */
    public MAccountChangeRecords selectMAccountChangeRecordsByUuid(String uuid);

    /**
     * 查询账变记录列表
     *
     * @param mAccountChangeRecords 账变记录
     * @return 账变记录集合
     */
    public List<MAccountChangeRecords> selectMAccountChangeRecordsList(MAccountChangeRecords mAccountChangeRecords);

    /**
     * 查询账变记录列表-个人的奖励历史记录
     *
     * @param
     * @return 账变记录集合
     */
    List<Map<String,Object>> selectMAccountChangeForeByUser();

    /**
     * 个人信息与近两日利润 前台《订单详细信息》页
     *
     * @param
     * @return
     */
    Map<String,Object> getUserProfitInfo();

    /**
     * 新增账变记录
     *
     * @param mAccountChangeRecords 账变记录
     * @return 结果
     */
    public int insertMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords);

    /**
     * 修改账变记录
     *
     * @param mAccountChangeRecords 账变记录
     * @return 结果
     */
    public int updateMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords);

    /**
     * 批量删除账变记录
     *
     * @param uuids 需要删除的账变记录主键集合
     * @return 结果
     */
    public int deleteMAccountChangeRecordsByUuids(String[] uuids);

    /**
     * 删除账变记录信息
     *
     * @param uuid 账变记录主键
     * @return 结果
     */
    public int deleteMAccountChangeRecordsByUuid(String uuid);
}
