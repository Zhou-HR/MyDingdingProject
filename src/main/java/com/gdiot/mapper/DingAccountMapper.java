package com.gdiot.mapper;

import com.gdiot.entity.DingAccount;

/**
 * @author ZhouHR
 */
public interface DingAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingAccount record);

    int insertSelective(DingAccount record);

    DingAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingAccount record);

    int updateByPrimaryKey(DingAccount record);
}