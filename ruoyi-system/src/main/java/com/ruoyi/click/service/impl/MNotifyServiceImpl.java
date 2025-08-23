package com.ruoyi.click.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.click.domain.MNotify;
import com.ruoyi.click.mapper.MNotifyMapper;
import com.ruoyi.click.service.IMNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-18
 */
@Service
public class MNotifyServiceImpl implements IMNotifyService
{
    @Autowired
    private MNotifyMapper mNotifyMapper;

    /**
     * 查询通知
     *
     * @param uid 通知主键
     * @return 通知
     */
    @Override
    public MNotify selectMNotifyByUid(Long uid)
    {
        return mNotifyMapper.selectMNotifyByUid(uid);
    }

    /**
     * 查询通知列表
     *
     * @param mNotify 通知
     * @return 通知
     */
    @Override
    public List<MNotify> selectMNotifyList(MNotify mNotify)
    {
        return mNotifyMapper.selectMNotifyList(mNotify);
    }

    /**
     * 新增通知
     *
     * @param mNotify 通知
     * @return 结果
     */
    @Override
    public int insertMNotify(MNotify mNotify)
    {
        mNotify.setCreateTime(DateUtils.getNowDate());
        return mNotifyMapper.insertMNotify(mNotify);
    }

    /**
     * 修改通知
     *
     * @param mNotify 通知
     * @return 结果
     */
    @Override
    public int updateMNotify(MNotify mNotify)
    {
        mNotify.setUpdateTime(DateUtils.getNowDate());
        return mNotifyMapper.updateMNotify(mNotify);
    }

    /**
     * 批量删除通知
     *
     * @param uids 需要删除的通知主键
     * @return 结果
     */
    @Override
    public int deleteMNotifyByUids(Long[] uids)
    {
        return mNotifyMapper.deleteMNotifyByUids(uids);
    }

    /**
     * 删除通知信息
     *
     * @param uid 通知主键
     * @return 结果
     */
    @Override
    public int deleteMNotifyByUid(Long uid)
    {
        return mNotifyMapper.deleteMNotifyByUid(uid);
    }

    /**
     * 统计数量
     *
     * @param mNotify
     * @return 结果
     */
    @Override
    public long countNum(MNotify mNotify)
    {
        return mNotifyMapper.countNum(mNotify);
    }
}
