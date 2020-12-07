package com.gdiot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhouHR
 */
@Data
public class DingMsgDataPo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String userid;

    private String msg;

}
