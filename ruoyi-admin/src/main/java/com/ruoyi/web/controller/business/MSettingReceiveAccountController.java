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
import com.ruoyi.business.domain.MSettingReceiveAccount;
import com.ruoyi.business.service.IMSettingReceiveAccountService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设置收款账户Controller
 * 
 * @author ruoyi
 * @date 2025-06-22
 */
@RestController
@RequestMapping("/api/settingReceiveAccount")
public class MSettingReceiveAccountController extends BaseController
{
    @Autowired
    private IMSettingReceiveAccountService mSettingReceiveAccountService;

    /**
     * 查询设置收款账户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MSettingReceiveAccount mSettingReceiveAccount)
    {
        startPage();
        List<MSettingReceiveAccount> list = mSettingReceiveAccountService.selectMSettingReceiveAccountList(mSettingReceiveAccount);
        return getDataTable(list);
    }

    /**
     * 导出设置收款账户列表
     */
    @Log(title = "设置收款账户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MSettingReceiveAccount mSettingReceiveAccount)
    {
        List<MSettingReceiveAccount> list = mSettingReceiveAccountService.selectMSettingReceiveAccountList(mSettingReceiveAccount);
        ExcelUtil<MSettingReceiveAccount> util = new ExcelUtil<MSettingReceiveAccount>(MSettingReceiveAccount.class);
        util.exportExcel(response, list, "设置收款账户数据");
    }

    /**
     * 获取设置收款账户详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mSettingReceiveAccountService.selectMSettingReceiveAccountById(id));
    }

    /**
     * 新增设置收款账户
     */
    @Log(title = "设置收款账户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MSettingReceiveAccount mSettingReceiveAccount)
    {
        return toAjax(mSettingReceiveAccountService.insertMSettingReceiveAccount(mSettingReceiveAccount));
    }

    /**
     * 修改设置收款账户
     */
    @Log(title = "设置收款账户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MSettingReceiveAccount mSettingReceiveAccount)
    {
        return toAjax(mSettingReceiveAccountService.updateMSettingReceiveAccount(mSettingReceiveAccount));
    }

    /**
     * 删除设置收款账户
     */
    @Log(title = "设置收款账户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mSettingReceiveAccountService.deleteMSettingReceiveAccountByIds(ids));
    }

    /**
     * 根据语言查询设置收款账户
     */
    @GetMapping("/getByLang")
    public AjaxResult getByLang(String lang)
    {
        return success(mSettingReceiveAccountService.selectByLang(lang));
    }

}
