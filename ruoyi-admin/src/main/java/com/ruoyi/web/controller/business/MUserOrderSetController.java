package com.ruoyi.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.click.domain.vo.UserOrderSetSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.business.domain.MUserOrderSet;
import com.ruoyi.business.service.IMUserOrderSetService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单设置Controller
 * 
 * @author ruoyi
 * @date 2025-06-30
 */
@RestController
@RequestMapping("/api/userOrderSet")
public class MUserOrderSetController extends BaseController
{
    @Autowired
    private IMUserOrderSetService mUserOrderSetService;

    /**
     * 查询订单设置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MUserOrderSet mUserOrderSet)
    {
        startPage();
        List<MUserOrderSet> list = mUserOrderSetService.selectMUserOrderSetList(mUserOrderSet);
        return getDataTable(list);
    }

    /**
     * 根据用户id查询订单设置列表
     */
    @GetMapping(value="/selectByUserId/{userId}")
    public AjaxResult selectByUserId(@PathVariable("userId") Long userId)
    {
        return success(mUserOrderSetService.selectByUserId(userId));
    }

    /**
     * 导出订单设置列表
     */
    @Log(title = "订单设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MUserOrderSet mUserOrderSet)
    {
        List<MUserOrderSet> list = mUserOrderSetService.selectMUserOrderSetList(mUserOrderSet);
        ExcelUtil<MUserOrderSet> util = new ExcelUtil<MUserOrderSet>(MUserOrderSet.class);
        util.exportExcel(response, list, "订单设置数据");
    }

    /**
     * 获取订单设置详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mUserOrderSetService.selectMUserOrderSetById(id));
    }

    /**
     * 新增订单设置
     */
    @Log(title = "订单设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MUserOrderSet mUserOrderSet)
    {
        return toAjax(mUserOrderSetService.insertMUserOrderSet(mUserOrderSet));
    }

    /**
     * 修改订单设置
     */
    @Log(title = "订单设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MUserOrderSet mUserOrderSet)
    {
        return toAjax(mUserOrderSetService.updateMUserOrderSet(mUserOrderSet));
    }

    /**
     * 删除订单设置
     */
    @Log(title = "订单设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mUserOrderSetService.deleteMUserOrderSetByIds(ids));
    }

    /**
     * 批量更新一个用户的设置
     */
    @Log(title = "批量更新一个用户的设置", businessType = BusinessType.UPDATE)
    @PostMapping("saveOrderSetByUser")
    public AjaxResult saveOrderSetByUser(@Validated @RequestBody UserOrderSetSaveVO vo)
    {
        return toAjax(mUserOrderSetService.saveOrderSetByUser(vo));
    }

}
