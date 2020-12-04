package com.gdiot.mapper;

import com.gdiot.entity.DingTravelList;

public interface DingTravelListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingTravelList record);

    int insertSelective(DingTravelList record);

    DingTravelList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingTravelList record);

    int updateByPrimaryKey(DingTravelList record);
}