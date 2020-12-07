package com.gdiot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhouHR
 */
@Data
public class DingUserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String user_id;

    private String user_detail;

}
