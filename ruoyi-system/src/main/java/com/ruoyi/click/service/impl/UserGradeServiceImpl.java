package com.ruoyi.click.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.click.domain.UserGrade;
import com.ruoyi.click.mapper.UserGradeMapper;
import com.ruoyi.click.service.IUserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户等级Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-15
 */
@Service
public class UserGradeServiceImpl extends ServiceImpl<UserGradeMapper, UserGrade> implements IUserGradeService
{
    @Autowired
    private UserGradeMapper userGradeMapper;

    /**
     * 查询用户等级
     *
     * @param id 用户等级主键
     * @return 用户等级
     */
    @Override
    public UserGrade selectUserGradeById(Long id)
    {
        return userGradeMapper.selectUserGradeById(id);
    }

    /**
     * 查询用户等级列表
     *
     * @param userGrade 用户等级
     * @return 用户等级
     */
    @Override
    public List<UserGrade> selectUserGradeList(UserGrade userGrade)
    {
        return userGradeMapper.selectUserGradeList(userGrade);
    }

    /**
     * 新增用户等级
     *
     * @param userGrade 用户等级
     * @return 结果
     */
    @Override
    public int insertUserGrade(UserGrade userGrade)
    {
        userGrade.setCreateTime(DateUtils.getNowDate());
        return userGradeMapper.insertUserGrade(userGrade);
    }

    /**
     * 修改用户等级
     *
     * @param userGrade 用户等级
     * @return 结果
     */
    @Override
    public int updateUserGrade(UserGrade userGrade)
    {
        userGrade.setUpdateTime(DateUtils.getNowDate());
        return userGradeMapper.updateUserGrade(userGrade);
    }

    /**
     * 批量删除用户等级
     *
     * @param ids 需要删除的用户等级主键
     * @return 结果
     */
    @Override
    public int deleteUserGradeByIds(Long[] ids)
    {
        return userGradeMapper.deleteUserGradeByIds(ids);
    }

    /**
     * 删除用户等级信息
     *
     * @param id 用户等级主键
     * @return 结果
     */
    @Override
    public int deleteUserGradeById(Long id)
    {
        return userGradeMapper.deleteUserGradeById(id);
    }
}
