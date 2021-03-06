package com.leyou.item.service.service;

import com.leyou.common.vo.PageResult;
import com.leyou.item.interf.domain.Brand;
import com.leyou.item.interf.rpo.BrandPageRpo;

import java.util.List;

/**
 * @author zc
 */
public interface BrandService {

    /**
     * 分页查询品牌
     * @param rpb
     * @return
     */
    PageResult<Brand> pageQueryBrands(BrandPageRpo rpb);

    /**
     * 保存品牌
     * @param brand
     * @param cids 品牌的三个级别Id
     */
    void saveBrand(Brand brand, List<Long> cids);

    /**
     * 更新品牌
     * @param brand
     * @param cids 品牌的三个级别Id
     */
    void updateBrand(Brand brand, List<Long> cids);

    /**
     * 根据品牌id获取品牌名称
     * @param id 品牌id
     * @return
     */
    Brand getBrandById(Long id);

    /**
     * 根据分类id查询该分类下的所有品牌
     * @param cid 分类id
     * @return
     */
    List<Brand> listBrandsByCid(Long cid);

    /**
     * 根据品牌id集合批量查询品牌
     * @param ids
     * @return
     */
    List<Brand> listBrandsByIds(List<Long> ids);
}
