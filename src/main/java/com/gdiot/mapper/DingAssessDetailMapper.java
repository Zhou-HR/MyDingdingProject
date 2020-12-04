package com.gdiot.mapper;

import com.gdiot.entity.DingAssessDetail;

public interface DingAssessDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingAssessDetail record);

    int insertSelective(DingAssessDetail record);

    DingAssessDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingAssessDetail record);

    int updateByPrimaryKey(DingAssessDetail record);
}