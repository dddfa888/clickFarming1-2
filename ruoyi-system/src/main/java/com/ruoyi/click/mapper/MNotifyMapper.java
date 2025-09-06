package com.ruoyi.click.mapper;

import com.ruoyi.click.domain.MNotify;
import org.apache.ibatis.annotations.*;

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


    /**
     * 提现新增消息
     * @param userId
     * @param loginAccount
     * @param title
     * @param content
     */
    @Insert("INSERT INTO m_notify (" +
            "user_id, is_read, user_name, create_time, update_time, " +  // 新增update_time
            "content, title" +
            ") VALUES (" +
            "#{userId}, #{read}, #{loginAccount}, NOW(), NOW(), " +  // 都用当前时间
            "#{content}, #{title}" +
            ")")
    void insertNotify(
            @Param("userId") Long userId,
            @Param("loginAccount") String loginAccount,
            @Param("title") String title,
            @Param("content") String content,
            @Param("read") int read
    );

    /**
     * 铃铛信息提示
     * @return
     */
    @Select("select count(1) from m_notify where user_id = #{userId} and is_read =0")
    int selectUnread(Long userId);

    /**
     * 消息发送
     * @param uid
     * @param loginAccount
     * @param read
     * @param title
     * @param content
     */
    @Insert("INSERT INTO m_notify (" +
            "user_id, is_read, user_name, create_time, update_time, " +
            "content, title" +
            ") VALUES (" +
            "#{uid}, #{read}, #{loginAccount}, NOW(), NOW(), " +
            "#{content}, #{title}" +
            ")")
    void insertNotify1(
            @Param("uid") Long uid,                // 对应user_id
            @Param("read") int read,               // 对应is_read（注意：移到第二位，与SQL顺序一致）
            @Param("loginAccount") String loginAccount,  // 对应user_name
            @Param("content") String content,      // 对应content
            @Param("title") String title           // 对应title
    );

    @Update("UPDATE m_notify SET is_read = 1, update_time = NOW() WHERE user_id = #{userId}")
    void updateAllReadByUserId(@Param("userId") Long userId);
}
