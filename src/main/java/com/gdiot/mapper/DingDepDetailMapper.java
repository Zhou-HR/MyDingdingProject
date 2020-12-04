package com.gdiot.mapper;

import com.gdiot.entity.DingDepDetail;

public interface DingDepDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingDepDetail record);

    int insertSelective(DingDepDetail record);

    DingDepDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingDepDetail record);

    int updateByPrimaryKey(DingDepDetail record);
}