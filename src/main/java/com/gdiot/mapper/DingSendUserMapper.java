package com.gdiot.mapper;

import com.gdiot.entity.DingSendUser;

public interface DingSendUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingSendUser record);

    int insertSelective(DingSendUser record);

    DingSendUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingSendUser record);

    int updateByPrimaryKey(DingSendUser record);
}