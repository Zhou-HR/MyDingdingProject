package com.gdiot.mapper;

import com.gdiot.entity.ProductPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 */
@Mapper
@Component
public interface ProductMapper {

    int insertProduct(ProductPo mProductPo);

    int selectProductCount();

    List<ProductPo> selectProductList();

}
