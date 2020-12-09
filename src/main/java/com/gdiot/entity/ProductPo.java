package com.gdiot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhouHR
 */
@Data
public class ProductPo {

    int id;
    String productName;
    String productType;
    String function;
    String doFor;
    String price;
    String reward;
    String productChief;
    String productPic;
    String productFile;
    String createTime;
}
