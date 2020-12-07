package com.gdiot.service;

import com.gdiot.entity.DingDingUser;

import java.util.List;

/**
 * @author ZhouHR
 */
public interface IDingDingUserService {
    /**
     * @param dingDingUser
     * @return
     */
    List<DingDingUser> selectOne(DingDingUser dingDingUser);

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
    int updateUserDep(DingDingUser dingDingUser);

    /**
     * @return
     */
    List<DingDingUser> selectAllUserId();
}
