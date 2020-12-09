package com.gdiot.controller;

import com.gdiot.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService mIProductService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test() {
        log.info("test===========@@@@@@@@@@@@@");
        log.info("测试页面");
        System.out.printf("测试页面");
    }

    @RequestMapping(value = "/getProductList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProductList(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<String, Object>();
        int pageNo = 1;
        int pageSize = 10;
        if (params != null) {
            try {
                if (params.containsKey("pageNo")) {
                    pageNo = Integer.valueOf(params.get("pageNo"));
                }
                if (params.containsKey("pageSize")) {
                    pageSize = Integer.valueOf(params.get("pageSize"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.put("error", 500);
                result.put("msg", "date format error!");
                return result;
            }
        }
        result = mIProductService.selectProductList();
        result.put("error", 0);
        result.put("msg", "success");
        return result;
    }

    @RequestMapping(value = "/setProductList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setProductList(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<String, Object>();
        String productName;
        String function;
        String doFor;
        String price;
        String reward;
        String productChief;
        String productPic;
        String productFile;
        if (params != null) {
            if (params.containsKey("productName")) {
                productName = params.get("productName");
            }
            if (params.containsKey("productName")) {
                productName = params.get("productName");
            }
        }
        result = mIProductService.selectProductList();
        result.put("error", 0);
        result.put("msg", "success");
        return result;
    }

}
