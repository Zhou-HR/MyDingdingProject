package com.gdiot.mapper;

import com.gdiot.entity.MaycurCode;

public interface MaycurCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MaycurCode record);

    int insertSelective(MaycurCode record);

    MaycurCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MaycurCode record);

    int updateByPrimaryKey(MaycurCode record);
}