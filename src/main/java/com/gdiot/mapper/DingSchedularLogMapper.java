package com.gdiot.mapper;

import com.gdiot.entity.DingSchedularLog;

public interface DingSchedularLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingSchedularLog record);

    int insertSelective(DingSchedularLog record);

    DingSchedularLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingSchedularLog record);

    int updateByPrimaryKey(DingSchedularLog record);
}