package com.gdiot.mapper;

import com.gdiot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 */
@Mapper
@Component
public interface UserMapper {
    /**
     * @param appKey
     * @param appSecret
     * @return
     */
    List<User> getUser(@Param("appKey") String appKey, @Param("appSecret") String appSecret);

}
