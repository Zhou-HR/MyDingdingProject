package com.gdiot.entity;

import lombok.Data;

/**
 * @author ZhouHR
 */
@Data
public class DingAccountPo {

    private int id;
    private String client;
    private String suiteId;
    private String appId;
    private String suiteKey;
    private String suiteSecret;

}
