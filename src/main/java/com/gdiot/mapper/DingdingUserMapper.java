package com.gdiot.mapper;

import com.gdiot.entity.DingdingUser;

public interface DingdingUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingdingUser record);

    int insertSelective(DingdingUser record);

    DingdingUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingdingUser record);

    int updateByPrimaryKey(DingdingUser record);
}