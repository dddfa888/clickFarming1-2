package com.ruoyi.click.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.click.domain.UserGrade;

import java.util.List;

/**
 * 用户等级Service接口
 *
 * @author ruoyi
 * @date 2025-06-15
 */
public interface IUserGradeService extends IService<UserGrade> {
    /**
     * 查询用户等级
     *
     * @param id 用户等级主键
     * @return 用户等级
     */
    public UserGrade selectUserGradeById(Long id);

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
     * 批量删除用户等级
     *
     * @param ids 需要删除的用户等级主键集合
     * @return 结果
     */
    public int deleteUserGradeByIds(Long[] ids);

    /**
     * 删除用户等级信息
     *
     * @param id 用户等级主键
     * @return 结果
     */
    public int deleteUserGradeById(Long id);
}
