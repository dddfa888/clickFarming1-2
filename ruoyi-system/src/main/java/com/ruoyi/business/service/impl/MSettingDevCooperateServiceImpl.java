package com.ruoyi.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.MSettingDevCooperateMapper;
import com.ruoyi.business.domain.MSettingDevCooperate;
import com.ruoyi.business.service.IMSettingDevCooperateService;

/**
 * 设置发展合作Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-22
 */
@Service
public class MSettingDevCooperateServiceImpl implements IMSettingDevCooperateService {
    @Autowired
    private MSettingDevCooperateMapper mSettingDevCooperateMapper;

    /**
     * 查询设置发展合作
     *
     * @param id 设置发展合作主键
     * @return 设置发展合作
     */
    @Override
    public MSettingDevCooperate selectMSettingDevCooperateById(Long id) {
        return mSettingDevCooperateMapper.selectMSettingDevCooperateById(id);
    }

    /**
     * 查询设置发展合作列表
     *
     * @param mSettingDevCooperate 设置发展合作
     * @return 设置发展合作
     */
    @Override
    public List<MSettingDevCooperate> selectMSettingDevCooperateList(MSettingDevCooperate mSettingDevCooperate) {
        return mSettingDevCooperateMapper.selectMSettingDevCooperateList(mSettingDevCooperate);
    }

    /**
     * 新增设置发展合作
     *
     * @param mSettingDevCooperate 设置发展合作
     * @return 结果
     */
    @Override
    public int insertMSettingDevCooperate(MSettingDevCooperate mSettingDevCooperate) {
        mSettingDevCooperate.setCreateTime(DateUtils.getNowDate());
        return mSettingDevCooperateMapper.insertMSettingDevCooperate(mSettingDevCooperate);
    }

    /**
     * 修改设置发展合作
     *
     * @param mSettingDevCooperate 设置发展合作
     * @return 结果
     */
    @Override
    public int updateMSettingDevCooperate(MSettingDevCooperate mSettingDevCooperate) {
        mSettingDevCooperate.setUpdateTime(DateUtils.getNowDate());
        return mSettingDevCooperateMapper.updateMSettingDevCooperate(mSettingDevCooperate);
    }

    /**
     * 批量删除设置发展合作
     *
     * @param ids 需要删除的设置发展合作主键
     * @return 结果
     */
    @Override
    public int deleteMSettingDevCooperateByIds(Long[] ids) {
        return mSettingDevCooperateMapper.deleteMSettingDevCooperateByIds(ids);
    }

    /**
     * 删除设置发展合作信息
     *
     * @param id 设置发展合作主键
     * @return 结果
     */
    @Override
    public int deleteMSettingDevCooperateById(Long id) {
        return mSettingDevCooperateMapper.deleteMSettingDevCooperateById(id);
    }

    /**
     * 根据语种查询设置发展合作信息
     *
     * @param lang 语种
     * @return 设置发展合作信息
     */
    @Override
    public MSettingDevCooperate selectByLang(String lang) {
        if (lang == null
                || lang.trim().isEmpty()
                || !(lang.equals("zh") || lang.equals("vi"))) {
            lang = "en";
        }
        return mSettingDevCooperateMapper.selectByLang(lang);
    }

}
