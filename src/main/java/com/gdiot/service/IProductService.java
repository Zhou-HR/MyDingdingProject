package com.gdiot.service;


import com.gdiot.entity.ProductPo;

import java.util.Map;

/**
 * @author ZhouHR
 */
public interface IProductService {

    int insertProduct(ProductPo mProductPo);

    Map<String, Object> selectProductList();
}
