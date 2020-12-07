package com.gdiot.entity;

import lombok.Data;

/**
 * @author ZhouHR
 */
@Data
public class DingAssessDetailPo {

    private String ddId;//钉钉ID

    private String assessId; //申请实例ID

    private String businessCode;//	审批编号

    private String auditResult;//审批结果

    private String auditStatus;//审批结果

    private String businessType;//业务类型

    private String depBizName;//所在部门

    private String provinceName;//省公司

    private String cityName;//地市

    private String applyReasonType;//申请事由类型

    private String applicantName;//申请人

    private String applicantPhone;//申请人电话

    private String entrustOperator;//委托运营商

    private String projectCode;//项目编码

    private String projectName;//项目名称

    private String deliveryTime;//计划交付时间

    private String operatorRent;//运营商年租金

    private String colocationRent;//场租年租金

    private String procurementCost;//采购总成本

    private String projectCost;//工程费

    private String memo;//情况描述

    private String detail;
    private String time;
    private String body;


}
