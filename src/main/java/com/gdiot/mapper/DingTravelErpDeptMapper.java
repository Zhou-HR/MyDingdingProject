package com.gdiot.mapper;

import com.gdiot.entity.DingTravelErpDept;

public interface DingTravelErpDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingTravelErpDept record);

    int insertSelective(DingTravelErpDept record);

    DingTravelErpDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingTravelErpDept record);

    int updateByPrimaryKey(DingTravelErpDept record);
}