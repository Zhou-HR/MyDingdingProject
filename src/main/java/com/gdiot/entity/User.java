package com.gdiot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private String appKey;

    private String appSecret;

}
