package com.gdiot.mapper;

import com.gdiot.entity.DingUserDetail;

public interface DingUserDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingUserDetail record);

    int insertSelective(DingUserDetail record);

    DingUserDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingUserDetail record);

    int updateByPrimaryKey(DingUserDetail record);
}