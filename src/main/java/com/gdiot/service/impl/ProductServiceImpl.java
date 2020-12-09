package com.gdiot.service.impl;

import com.gdiot.entity.ProductPo;
import com.gdiot.mapper.ProductMapper;
import com.gdiot.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhouHR
 */
@Slf4j
@Service("ProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper mProductMapper;

    @Override
    public int insertProduct(ProductPo mProductPo) {
        return mProductMapper.insertProduct(mProductPo);
    }

    @Override
    public Map<String, Object> selectProductList() {
        log.info("begin selectProductList");
        Map<String, Object> result = new HashMap<String, Object>();
        int count = mProductMapper.selectProductCount();
        log.info("selectList count=" + count);

//		if (pageSize < 1 || pageSize > count) {
//			pageSize = 20;
//		}
//		int totalPage = count / pageSize;
//		if (pageNo < 1 || pageSize > totalPage) {
//			pageNo = 1;
//		}
//		
//		int limit = pageSize;
//		int offset = (pageNo-1) * pageSize;
//		
//		if(source == null || source =="") {
//			source = null;
//		}

        List<ProductPo> list = mProductMapper.selectProductList();
        log.info("selectProductList  Size=" + list.size());
//		result.put("totalPage", totalPage);
//		result.put("pageNo", pageNo);
//		result.put("pageSize", pageSize);
        result.put("count", count);
        result.put("list", list);
        return result;
    }

}
