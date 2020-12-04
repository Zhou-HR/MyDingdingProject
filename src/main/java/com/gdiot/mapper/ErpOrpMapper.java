package com.gdiot.mapper;

import com.gdiot.entity.ErpOrp;

public interface ErpOrpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ErpOrp record);

    int insertSelective(ErpOrp record);

    ErpOrp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ErpOrp record);

    int updateByPrimaryKey(ErpOrp record);
}