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
import com.ruoyi.business.domain.MSettingGlobal;
import com.ruoyi.business.service.IMSettingGlobalService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设置全球化Controller
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@RestController
@RequestMapping("/api/settingGlobal")
public class MSettingGlobalController extends BaseController
{
    @Autowired
    private IMSettingGlobalService mSettingGlobalService;

    /**
     * 查询设置全球化列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MSettingGlobal mSettingGlobal)
    {
        startPage();
        List<MSettingGlobal> list = mSettingGlobalService.selectMSettingGlobalList(mSettingGlobal);
        return getDataTable(list);
    }

    /**
     * 导出设置全球化列表
     */
    @Log(title = "设置全球化", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MSettingGlobal mSettingGlobal)
    {
        List<MSettingGlobal> list = mSettingGlobalService.selectMSettingGlobalList(mSettingGlobal);
        ExcelUtil<MSettingGlobal> util = new ExcelUtil<MSettingGlobal>(MSettingGlobal.class);
        util.exportExcel(response, list, "设置全球化数据");
    }

    /**
     * 获取设置全球化详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mSettingGlobalService.selectMSettingGlobalById(id));
    }

    /**
     * 新增设置全球化
     */
    @Log(title = "设置全球化", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MSettingGlobal mSettingGlobal)
    {
        return toAjax(mSettingGlobalService.insertMSettingGlobal(mSettingGlobal));
    }

    /**
     * 修改设置全球化
     */
    @Log(title = "设置全球化", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MSettingGlobal mSettingGlobal)
    {
        return toAjax(mSettingGlobalService.updateMSettingGlobal(mSettingGlobal));
    }

    /**
     * 删除设置全球化
     */
    @Log(title = "设置全球化", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mSettingGlobalService.deleteMSettingGlobalByIds(ids));
    }

    /**
     * 根据语言查询设置全球化
     */
    @GetMapping("/getByLang")
    public AjaxResult getByLang(String lang)
    {
        return success(mSettingGlobalService.selectByLang(lang));
    }

}
