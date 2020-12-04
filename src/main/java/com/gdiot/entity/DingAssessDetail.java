package com.gdiot.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ding_account
 * @author 
 */
@Data
public class DingAssessDetail implements Serializable {
    private Integer id;

    private String client;

    private String suiteid;

    private String appid;

    private String suitekey;

    private String suitesecret;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}