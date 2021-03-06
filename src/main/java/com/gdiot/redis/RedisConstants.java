package com.gdiot.redis;

/**
 * @author ZhouHR
 */
public class RedisConstants {

    public static final String SPILT = ":";

    /**
     * redis库1  保存档案树
     */
    public static final Integer DATEBASE_1 = 1;

    /**
     * 1.redis库2 保存档案表格
     * 2.保存分页码
     */
    public static final Integer DATEBASE_2 = 2;

    /**
     * redis库3 保存档案image url
     */
    public static final Integer DATEBASE_3 = 3;

    /**
     * 1.redis库4 保存手机验证码
     */
    public static final Integer DATEBASE_4 = 4;

    /**
     * redis库5 保存身份认证信息
     */
    public static final Integer DATEBASE_5 = 5;

    /**
     * redis库6 记录身份认证次数
     */
    public static final Integer DATEBASE_6 = 6;

    /**
     * redis库7 记录重发次数
     */
    public static final Integer DATEBASE_7 = 7;

    /**
     * redis库8 记录任务参数
     */
    public static final Integer DATEBASE_8 = 8;


    public RedisConstants() {

    }
}