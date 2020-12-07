package com.gdiot.controller;

import com.gdiot.entity.DingDingUser;
import com.gdiot.mapper.DingDingUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhouHR
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestAction {

    @Autowired
    private DingDingUserMapper dingDingUserMapper;

    @RequestMapping("/selectTbData")
    public List<DingDingUser> selectTbData() {

        DingDingUser dingDingUser = new DingDingUser();

        dingDingUser.setDdId("virus");

        dingDingUserMapper.insertDingDingUser(dingDingUser);
        dingDingUserMapper.updateidDingDingUser(dingDingUser);
        return null;

    }

    @GetMapping(value = "/test")
    public void test() {
        log.info("test===========@@@@@@@@@@@@@");
        log.info("测试页面");
        System.out.printf("测试页面");
    }
}
