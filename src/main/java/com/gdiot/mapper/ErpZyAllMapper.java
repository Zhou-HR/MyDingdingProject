package com.gdiot.mapper;

import com.gdiot.entity.ErpZyAll;

public interface ErpZyAllMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ErpZyAll record);

    int insertSelective(ErpZyAll record);

    ErpZyAll selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ErpZyAll record);

    int updateByPrimaryKey(ErpZyAll record);
}