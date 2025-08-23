package com.ruoyi.click.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.MUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author ruoyi
 * @date 2025-06-15
 */
@Mapper
public interface MUserMapper extends BaseMapper<MUser> {
    /**
     * 查询用户
     *
     * @param uid 用户主键
     * @return 用户
     */
    public MUser selectMUserByUid(Long uid);

    /**
     * 查询用户列表
     *
     * @param mUser 用户
     * @return 用户集合
     */
    public List<MUser> selectMUserList(MUser mUser);

    /**
     * 新增用户
     *
     * @param mUser 用户
     * @return 结果
     */
    public int insertMUser(MUser mUser);

    /**
     * 修改用户
     *
     * @param mUser 用户
     * @return 结果
     */
    public int updateMUser(MUser mUser);

    /**
     * 删除用户
     *
     * @param uid 用户主键
     * @return 结果
     */
    public int deleteMUserByUid(Long uid);

    /**
     * 批量删除用户
     *
     * @param uids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMUserByUids(Long[] uids);

    /**
     * 用户当天刷单数量清零
     */
    void clearBrushNumber();

    /**
     * 用户当日订单数量加1
     * @param uid 用户主键
     * @return
     */
    int increaseBrushNumber(@Param("uid") Long uid, @Param("num") Integer num);
}
