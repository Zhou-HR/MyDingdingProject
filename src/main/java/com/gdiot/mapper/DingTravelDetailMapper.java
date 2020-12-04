package com.gdiot.mapper;

import com.gdiot.entity.DingTravelDetail;

public interface DingTravelDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingTravelDetail record);

    int insertSelective(DingTravelDetail record);

    DingTravelDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingTravelDetail record);

    int updateByPrimaryKey(DingTravelDetail record);
}