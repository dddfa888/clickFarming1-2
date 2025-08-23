package com.ruoyi.business.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.business.domain.MRewardRecord;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

/**
 * 奖励记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-28
 */
@Mapper
public interface MRewardRecordMapper
{
    /**
     * 查询奖励记录
     * 
     * @param id 奖励记录主键
     * @return 奖励记录
     */
    MRewardRecord selectMRewardRecordById(Long id);

    /**
     * 查询一个用户的奖励记录
     * 
     * @param userId
     * @return 奖励记录
     */
    List<MRewardRecord> selectByUserId(Long userId);

    /**
     * 查询一个用户的奖励记录，适用前端
     *
     * @param userId
     * @return 奖励记录
     */
    @MapKey("id")
    List<Map<String,Object>> selectSimpleByUserId(Long userId);

    /**
     * 查询奖励记录列表
     * 
     * @param mRewardRecord 奖励记录
     * @return 奖励记录集合
     */
    List<MRewardRecord> selectMRewardRecordList(MRewardRecord mRewardRecord);

    /**
     * 新增奖励记录
     * 
     * @param mRewardRecord 奖励记录
     * @return 结果
     */
    int insertMRewardRecord(MRewardRecord mRewardRecord);

    /**
     * 修改奖励记录
     * 
     * @param mRewardRecord 奖励记录
     * @return 结果
     */
    int updateMRewardRecord(MRewardRecord mRewardRecord);

    /**
     * 删除奖励记录
     * 
     * @param id 奖励记录主键
     * @return 结果
     */
    int deleteMRewardRecordById(Long id);

    /**
     * 批量删除奖励记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMRewardRecordByIds(Long[] ids);
}
