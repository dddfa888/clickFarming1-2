package com.ruoyi.click.mapper;

import com.ruoyi.click.domain.MNotify;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 通知Mapper接口
 *
 * @author ruoyi
 * @date 2025-06-18
 */
@Mapper
public interface MNotifyMapper {
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
     * 删除通知
     *
     * @param uid 通知主键
     * @return 结果
     */
    public int deleteMNotifyByUid(Long uid);

    /**
     * 批量删除通知
     *
     * @param uids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMNotifyByUids(Long[] uids);


    /**
     * 统计数量
     *
     * @param mNotify
     * @return 通知集合
     */
    long countNum(MNotify mNotify);
}
