package com.gdiot.mapper;

import com.gdiot.entity.ErpOrpAll;

public interface ErpOrpAllMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ErpOrpAll record);

    int insertSelective(ErpOrpAll record);

    ErpOrpAll selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ErpOrpAll record);

    int updateByPrimaryKey(ErpOrpAll record);
}