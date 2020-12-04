package com.gdiot.mapper;

import com.gdiot.entity.ErpZy;

public interface ErpZyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ErpZy record);

    int insertSelective(ErpZy record);

    ErpZy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ErpZy record);

    int updateByPrimaryKey(ErpZy record);
}