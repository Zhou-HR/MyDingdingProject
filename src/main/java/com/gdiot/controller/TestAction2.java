package com.gdiot.controller;

import com.gdiot.entity.DingUserResult;
import com.gdiot.mapper.DingDingUserMapper;
import com.gdiot.redis.JedisUtil;
import com.gdiot.session.RequestProcess;
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
@RequestMapping("/test2")
public class TestAction2 {

    @Autowired
    private DingDingUserMapper dingDingUserMapper;

    @Autowired
    JedisUtil jedisUtil;

    @RequestMapping("/wait")
    public List<DingUserResult> wait1() {

        try {
            Thread.sleep(900000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dingDingUserMapper.selectDingDingUser(1, 10);

    }

    @RequestMapping("/selectTbData")
    @RequestProcess
    public List<DingUserResult> selectTbData() {

        jedisUtil.set("test", "test");

        return dingDingUserMapper.selectDingDingUser(1, 10);

    }

    @GetMapping(value = "/test")
    public void test() {
        log.info("test===========@@@@@@@@@@@@@");
        log.info("测试页面");
        System.out.printf("测试页面");
    }
}
