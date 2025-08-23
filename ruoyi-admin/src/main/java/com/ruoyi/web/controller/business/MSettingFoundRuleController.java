package com.ruoyi.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.business.domain.MSettingFoundRule;
import com.ruoyi.business.service.IMSettingFoundRuleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设置基本原则Controller
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@RestController
@RequestMapping("/api/settingFoundRule")
public class MSettingFoundRuleController extends BaseController
{
    @Autowired
    private IMSettingFoundRuleService mSettingFoundRuleService;

    /**
     * 查询设置基本原则列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MSettingFoundRule mSettingFoundRule)
    {
        startPage();
        List<MSettingFoundRule> list = mSettingFoundRuleService.selectMSettingFoundRuleList(mSettingFoundRule);
        return getDataTable(list);
    }

    /**
     * 导出设置基本原则列表
     */
    @Log(title = "设置基本原则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MSettingFoundRule mSettingFoundRule)
    {
        List<MSettingFoundRule> list = mSettingFoundRuleService.selectMSettingFoundRuleList(mSettingFoundRule);
        ExcelUtil<MSettingFoundRule> util = new ExcelUtil<MSettingFoundRule>(MSettingFoundRule.class);
        util.exportExcel(response, list, "设置基本原则数据");
    }

    /**
     * 获取设置基本原则详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mSettingFoundRuleService.selectMSettingFoundRuleById(id));
    }

    /**
     * 新增设置基本原则
     */
    @Log(title = "设置基本原则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MSettingFoundRule mSettingFoundRule)
    {
        return toAjax(mSettingFoundRuleService.insertMSettingFoundRule(mSettingFoundRule));
    }

    /**
     * 修改设置基本原则
     */
    @Log(title = "设置基本原则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MSettingFoundRule mSettingFoundRule)
    {
        return toAjax(mSettingFoundRuleService.updateMSettingFoundRule(mSettingFoundRule));
    }

    /**
     * 删除设置基本原则
     */
    @Log(title = "设置基本原则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mSettingFoundRuleService.deleteMSettingFoundRuleByIds(ids));
    }

    /**
     * 根据语言查询设置基本原则
     */
    @GetMapping("/getByLang")
    public AjaxResult getByLang(String lang)
    {
        return success(mSettingFoundRuleService.selectByLang(lang));
    }

}
