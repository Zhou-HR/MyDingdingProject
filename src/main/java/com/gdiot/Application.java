package com.gdiot;

import com.gdiot.service.AsyncService;
import com.gdiot.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ZhouHR
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.gdiot.mapper")
@EnableScheduling
public class Application {

    @Autowired
    private static AsyncService asyncService;
    private static ApplicationContext context;

    public static void main(String[] args) {
        log.info("-------------SpMeterDataApplication run----------------");

        SpringApplication application = new SpringApplication(Application.class);
        context = application.run(args);

        SpringContextUtils mSpringContextUtils = new SpringContextUtils();
        mSpringContextUtils.setApplicationContext(context);
    }
}
