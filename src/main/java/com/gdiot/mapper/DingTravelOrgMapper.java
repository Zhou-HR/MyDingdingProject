package com.gdiot.mapper;

import com.gdiot.entity.DingTravelOrg;

public interface DingTravelOrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingTravelOrg record);

    int insertSelective(DingTravelOrg record);

    DingTravelOrg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingTravelOrg record);

    int updateByPrimaryKey(DingTravelOrg record);
}