package com.ruoyi.click.mapper;

import com.ruoyi.click.domain.MAccountChangeRecords;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 账变记录Mapper接口
 *
 * @author ruoyi
 * @date 2025-06-16
 */
@Mapper
public interface MAccountChangeRecordsMapper
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
     * @param mAccountChangeRecords 账变记录
     * @return 账变记录集合
     */
    @MapKey("id")
    List<Map<String,Object>> selectMAccountChangeForeByUser(MAccountChangeRecords mAccountChangeRecords);

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
     * 删除账变记录
     *
     * @param uuid 账变记录主键
     * @return 结果
     */
    public int deleteMAccountChangeRecordsByUuid(String uuid);

    /**
     * 批量删除账变记录
     *
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMAccountChangeRecordsByUuids(String[] uuids);

    /**
     * 一个用户一天内的利润总计
     * @param param
     * @return 统计数量结果
     */
    BigDecimal sumAmountByUserDate(Map<String,Object> param);
}
