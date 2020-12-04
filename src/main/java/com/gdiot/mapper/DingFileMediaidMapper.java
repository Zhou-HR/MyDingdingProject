package com.gdiot.mapper;

import com.gdiot.entity.DingFileMediaid;

public interface DingFileMediaidMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingFileMediaid record);

    int insertSelective(DingFileMediaid record);

    DingFileMediaid selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingFileMediaid record);

    int updateByPrimaryKey(DingFileMediaid record);
}