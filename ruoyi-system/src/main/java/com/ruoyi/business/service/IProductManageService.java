package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.ProductManage;

/**
 * 产品管理Service接口
 * 
 * @author ruoyi
 * @date 2025-06-15
 */
public interface IProductManageService 
{
    /**
     * 查询产品管理
     * 
     * @param id 产品管理主键
     * @return 产品管理
     */
    ProductManage selectProductManageById(Long id);

    /**
     * 查询产品管理列表
     * 
     * @param productManage 产品管理
     * @return 产品管理集合
     */
    List<ProductManage> selectProductManageList(ProductManage productManage);

    /**
     * 新增产品管理
     * 
     * @param productManage 产品管理
     * @return 结果
     */
    int insertProductManage(ProductManage productManage);

    /**
     * 修改产品管理
     * 
     * @param productManage 产品管理
     * @return 结果
     */
    int updateProductManage(ProductManage productManage);

    /**
     * 批量删除产品管理
     * 
     * @param ids 需要删除的产品管理主键集合
     * @return 结果
     */
    int deleteProductManageByIds(Long[] ids);

    /**
     * 删除产品管理信息
     * 
     * @param id 产品管理主键
     * @return 结果
     */
    int deleteProductManageById(Long id);
}
