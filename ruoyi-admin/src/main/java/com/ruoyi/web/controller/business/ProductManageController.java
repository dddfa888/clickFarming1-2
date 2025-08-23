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
import com.ruoyi.business.domain.ProductManage;
import com.ruoyi.business.service.IProductManageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品管理Controller
 * 
 * @author ruoyi
 * @date 2025-06-15
 */
@RestController
@RequestMapping("/api/product")
public class ProductManageController extends BaseController
{
    @Autowired
    private IProductManageService productManageService;

    /**
     * 查询产品管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProductManage productManage)
    {
        startPage();
        List<ProductManage> list = productManageService.selectProductManageList(productManage);
        return getDataTable(list);
    }

    /**
     * 导出产品管理列表
     */
    @Log(title = "产品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductManage productManage)
    {
        List<ProductManage> list = productManageService.selectProductManageList(productManage);
        ExcelUtil<ProductManage> util = new ExcelUtil<ProductManage>(ProductManage.class);
        util.exportExcel(response, list, "产品管理数据");
    }

    /**
     * 获取产品管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(productManageService.selectProductManageById(id));
    }

    /**
     * 新增产品管理
     */
    @Log(title = "产品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductManage productManage)
    {
        return toAjax(productManageService.insertProductManage(productManage));
    }

    /**
     * 修改产品管理
     */
    @Log(title = "产品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductManage productManage)
    {
        return toAjax(productManageService.updateProductManage(productManage));
    }

    /**
     * 删除产品管理
     */
    @Log(title = "产品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(productManageService.deleteProductManageByIds(ids));
    }
}
