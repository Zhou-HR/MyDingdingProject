package com.gdiot.mapper;

import com.gdiot.entity.DingSendLog;

public interface DingSendLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingSendLog record);

    int insertSelective(DingSendLog record);

    DingSendLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingSendLog record);

    int updateByPrimaryKey(DingSendLog record);
}