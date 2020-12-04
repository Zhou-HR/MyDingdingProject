package com.gdiot.mapper;

import com.gdiot.entity.DingTravelPushDataRecord;

public interface DingTravelPushDataRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingTravelPushDataRecord record);

    int insertSelective(DingTravelPushDataRecord record);

    DingTravelPushDataRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingTravelPushDataRecord record);

    int updateByPrimaryKey(DingTravelPushDataRecord record);
}