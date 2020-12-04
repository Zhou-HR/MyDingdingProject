package com.gdiot.mapper;

import com.gdiot.entity.DingAssessList;

public interface DingAssessListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingAssessList record);

    int insertSelective(DingAssessList record);

    DingAssessList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingAssessList record);

    int updateByPrimaryKey(DingAssessList record);
}