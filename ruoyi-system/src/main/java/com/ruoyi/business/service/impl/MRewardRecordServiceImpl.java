package com.ruoyi.business.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.MRewardRecordMapper;
import com.ruoyi.business.domain.MRewardRecord;
import com.ruoyi.business.service.IMRewardRecordService;

/**
 * 奖励记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-28
 */
@Service
public class MRewardRecordServiceImpl implements IMRewardRecordService 
{
    @Autowired
    private MRewardRecordMapper mRewardRecordMapper;

    /**
     * 查询奖励记录
     * 
     * @param id 奖励记录主键
     * @return 奖励记录
     */
    @Override
    public MRewardRecord selectMRewardRecordById(Long id)
    {
        return mRewardRecordMapper.selectMRewardRecordById(id);
    }

    /**
     * 查询一个用户的奖励记录
     * 
     * @param userId
     * @return 奖励记录
     */
    @Override
    public List<MRewardRecord> selectByUserId(Long userId)
    {
        return mRewardRecordMapper.selectByUserId(userId);
    }

    /**
     * 查询一个用户的奖励记录，适用前端
     *
     * @param userId
     * @return 奖励记录
     */
    @Override
    public List<Map<String,Object>> selectSimpleByUserId(Long userId)
    {
        return mRewardRecordMapper.selectSimpleByUserId(userId);
    }

    /**
     * 查询奖励记录列表
     * 
     * @param mRewardRecord 奖励记录
     * @return 奖励记录
     */
    @Override
    public List<MRewardRecord> selectMRewardRecordList(MRewardRecord mRewardRecord)
    {
        return mRewardRecordMapper.selectMRewardRecordList(mRewardRecord);
    }

    /**
     * 新增奖励记录
     * 
     * @param mRewardRecord 奖励记录
     * @return 结果
     */
    @Override
    public int insertMRewardRecord(MRewardRecord mRewardRecord)
    {
        mRewardRecord.setCreateTime(DateUtils.getNowDate());
        return mRewardRecordMapper.insertMRewardRecord(mRewardRecord);
    }

    /**
     * 修改奖励记录
     * 
     * @param mRewardRecord 奖励记录
     * @return 结果
     */
    @Override
    public int updateMRewardRecord(MRewardRecord mRewardRecord)
    {
        mRewardRecord.setUpdateTime(DateUtils.getNowDate());
        return mRewardRecordMapper.updateMRewardRecord(mRewardRecord);
    }

    /**
     * 批量删除奖励记录
     * 
     * @param ids 需要删除的奖励记录主键
     * @return 结果
     */
    @Override
    public int deleteMRewardRecordByIds(Long[] ids)
    {
        return mRewardRecordMapper.deleteMRewardRecordByIds(ids);
    }

    /**
     * 删除奖励记录信息
     * 
     * @param id 奖励记录主键
     * @return 结果
     */
    @Override
    public int deleteMRewardRecordById(Long id)
    {
        return mRewardRecordMapper.deleteMRewardRecordById(id);
    }
}
