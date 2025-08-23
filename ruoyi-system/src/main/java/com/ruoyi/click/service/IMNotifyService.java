package com.ruoyi.click.service;

import com.ruoyi.click.domain.MNotify;

import java.util.List;

/**
 * 通知Service接口
 *
 * @author ruoyi
 * @date 2025-06-18
 */
public interface IMNotifyService
{
    /**
     * 查询通知
     *
     * @param uid 通知主键
     * @return 通知
     */
    public MNotify selectMNotifyByUid(Long uid);

    /**
     * 查询通知列表
     *
     * @param mNotify 通知
     * @return 通知集合
     */
    public List<MNotify> selectMNotifyList(MNotify mNotify);

    /**
     * 新增通知
     *
     * @param mNotify 通知
     * @return 结果
     */
    public int insertMNotify(MNotify mNotify);

    /**
     * 修改通知
     *
     * @param mNotify 通知
     * @return 结果
     */
    public int updateMNotify(MNotify mNotify);

    /**
     * 批量删除通知
     *
     * @param uids 需要删除的通知主键集合
     * @return 结果
     */
    public int deleteMNotifyByUids(Long[] uids);

    /**
     * 删除通知信息
     *
     * @param uid 通知主键
     * @return 结果
     */
    public int deleteMNotifyByUid(Long uid);

    /**
     * 统计数量
     *
     * @param mNotify
     * @return 结果
     */
    long countNum(MNotify mNotify);

}
