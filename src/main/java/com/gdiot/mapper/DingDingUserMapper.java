package com.gdiot.mapper;

import com.gdiot.entity.DingDingUser;
import com.gdiot.entity.DingUserResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 */
@Mapper
@Component
public interface DingDingUserMapper {

    /**
     * @param dingDingUser
     * @return
     */
    int insertDingDingUser(DingDingUser dingDingUser);

    /**
     * @param dingDingUser
     * @return
     */
    int updateidDingDingUser(DingDingUser dingDingUser);

    /**
     * @param dingDingUser
     * @return
     */
    List<DingDingUser> selectOne(DingDingUser dingDingUser);

    /**
     * @param dingDingUser
     * @return
     */

    int updateUserDep(DingDingUser dingDingUser);

    /**
     * @return
     */
    List<DingDingUser> selectAllUserId();

    /**
     * @param limit
     * @param offset
     * @return
     */
    List<DingUserResult> selectDingDingUser(@Param("limit") int limit, @Param("offset") int offset);

    /**
     * @return
     */
    int selectDingDingUserCount();
}
