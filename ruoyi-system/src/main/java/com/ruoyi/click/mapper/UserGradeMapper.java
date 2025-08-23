package com.ruoyi.click.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.click.domain.UserGrade;
import org.apache.ibatis.annotations.Mapper;;

/**
 * 用户等级Mapper接口
 *
 * @author ruoyi
 * @date 2025-06-15
 */
@Mapper
public interface UserGradeMapper extends BaseMapper<UserGrade>
{
    /**
     * 查询用户等级
     *
     * @param id 用户等级主键
     * @return 用户等级
     */
    public UserGrade selectUserGradeById(Long id);

    /**
     * 查询用户等级
     *
     * @param sortNum 用户等级序号
     * @return 用户等级
     */
    UserGrade selectUserGradeBySortNum(Integer sortNum);

    /**
     * 查询用户等级列表
     *
     * @param userGrade 用户等级
     * @return 用户等级集合
     */
    public List<UserGrade> selectUserGradeList(UserGrade userGrade);

    /**
     * 新增用户等级
     *
     * @param userGrade 用户等级
     * @return 结果
     */
    public int insertUserGrade(UserGrade userGrade);

    /**
     * 修改用户等级
     *
     * @param userGrade 用户等级
     * @return 结果
     */
    public int updateUserGrade(UserGrade userGrade);

    /**
     * 删除用户等级
     *
     * @param id 用户等级主键
     * @return 结果
     */
    public int deleteUserGradeById(Long id);

    /**
     * 批量删除用户等级
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserGradeByIds(Long[] ids);
}
