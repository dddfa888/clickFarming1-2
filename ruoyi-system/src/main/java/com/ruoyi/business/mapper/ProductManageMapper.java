package com.ruoyi.business.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.business.domain.ProductManage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品管理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-15
 */
@Mapper
public interface ProductManageMapper
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
     * 删除产品管理
     * 
     * @param id 产品管理主键
     * @return 结果
     */
    int deleteProductManageById(Long id);

    /**
     * 批量删除产品管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteProductManageByIds(Long[] ids);

    /**
     * 查询总数量
     *
     * @param
     * @return 结果
     */
    long countNum(ProductManage productManage);

    /**
     * 查询列表片段
     * @param param 含2个参数：beginIndex-起始行的位置，num-取多少行数据
     * @return
     */
    List<ProductManage> selectProductManageByLimit(Map<String,Object> param);

    /**
     * 根据下标查询部分数据
     *
     * @param
     * @return 结果
     */
    ProductManage selectProductManageByRandom();

    /**
     * 根据条件查询符合条件的数据id
     *
     * @param
     * @return 结果
     */
    List<Long> getIdList(Map<String,Object> param);
    /**
     * 根据条件查询符合条件的数据id
     *
     * @param
     * @return 结果
     */
    List<Long> getIdListByOrderSet(Map<String,Object> param);
}
