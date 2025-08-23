package com.ruoyi.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.ProductManageMapper;
import com.ruoyi.business.domain.ProductManage;
import com.ruoyi.business.service.IProductManageService;

/**
 * 产品管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-15
 */
@Service
public class ProductManageServiceImpl implements IProductManageService 
{
    @Autowired
    private ProductManageMapper productManageMapper;

    /**
     * 查询产品管理
     * 
     * @param id 产品管理主键
     * @return 产品管理
     */
    @Override
    public ProductManage selectProductManageById(Long id)
    {
        return productManageMapper.selectProductManageById(id);
    }

    /**
     * 查询产品管理列表
     * 
     * @param productManage 产品管理
     * @return 产品管理
     */
    @Override
    public List<ProductManage> selectProductManageList(ProductManage productManage)
    {
        return productManageMapper.selectProductManageList(productManage);
    }

    /**
     * 新增产品管理
     * 
     * @param productManage 产品管理
     * @return 结果
     */
    @Override
    public int insertProductManage(ProductManage productManage)
    {
        productManage.setCreateTime(DateUtils.getNowDate());
        return productManageMapper.insertProductManage(productManage);
    }

    /**
     * 修改产品管理
     * 
     * @param productManage 产品管理
     * @return 结果
     */
    @Override
    public int updateProductManage(ProductManage productManage)
    {
        productManage.setUpdateTime(DateUtils.getNowDate());
        return productManageMapper.updateProductManage(productManage);
    }

    /**
     * 批量删除产品管理
     * 
     * @param ids 需要删除的产品管理主键
     * @return 结果
     */
    @Override
    public int deleteProductManageByIds(Long[] ids)
    {
        return productManageMapper.deleteProductManageByIds(ids);
    }

    /**
     * 删除产品管理信息
     * 
     * @param id 产品管理主键
     * @return 结果
     */
    @Override
    public int deleteProductManageById(Long id)
    {
        return productManageMapper.deleteProductManageById(id);
    }
}
