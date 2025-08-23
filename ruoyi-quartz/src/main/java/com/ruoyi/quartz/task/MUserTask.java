package com.ruoyi.quartz.task;

import com.ruoyi.click.mapper.MUserMapper;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度：前台用户
 * 
 * @author ruoyi
 */
@Component("MUserTask")
public class MUserTask
{
    @Autowired
    private MUserMapper mUserMapper;

    /**
     * 凌晨，用户当天刷单数量清零
     */
    public void clearBrushNumber()
    {
        mUserMapper.clearBrushNumber();
    }
}
