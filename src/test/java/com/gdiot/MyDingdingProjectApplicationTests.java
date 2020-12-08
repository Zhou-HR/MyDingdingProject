package com.gdiot;


import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * 添加测试类，用于数据的验证是否正确
 *
 * @author ZhouHR
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MyDingdingProjectApplicationTests {

    @Before
    public void init() {
        System.out.println("开始测试=====================");
    }

    @After
    public void after() {
        System.out.println("结束测试=====================");
    }
}
