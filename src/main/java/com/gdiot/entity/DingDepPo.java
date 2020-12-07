package com.gdiot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhouHR
 */
@Data
public class DingDepPo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String dep_id;

    private String dep_detail;

    private String dep_name;

    String parent_id;

    String Code;

    String DeptManagerUseridList;

    String DeptPerimits;

    String DeptPermits;

    String UserPerimits;

    String UserPermits;

}
