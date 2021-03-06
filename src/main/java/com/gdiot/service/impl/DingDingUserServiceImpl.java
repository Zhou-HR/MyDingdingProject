package com.gdiot.service.impl;

import com.gdiot.entity.DingDingUser;
import com.gdiot.mapper.DingDingUserMapper;
import com.gdiot.service.IDingDingUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhouHR
 */
@Slf4j
@Service("DingDingUserService")
public class DingDingUserServiceImpl implements IDingDingUserService {

    @Autowired
    private DingDingUserMapper mDingDingUserMapper;

    @Override
    public List<DingDingUser> selectOne(DingDingUser dingDingUser) {
        return mDingDingUserMapper.selectOne(dingDingUser);
    }

    @Override
    public int insertDingDingUser(DingDingUser dingDingUser) {
        //查询是否有，有的话替换，无的话插入
        List<DingDingUser> list = mDingDingUserMapper.selectOne(dingDingUser);
        if (list != null && list.size() > 0) {//已存在
            return mDingDingUserMapper.updateidDingDingUser(dingDingUser);
        } else {//不存在
            return mDingDingUserMapper.insertDingDingUser(dingDingUser);
        }
    }

    @Override
    public int updateidDingDingUser(DingDingUser dingDingUser) {
        return mDingDingUserMapper.updateidDingDingUser(dingDingUser);
    }

    @Override
    public int updateUserDep(DingDingUser dingDingUser) {
        return mDingDingUserMapper.updateUserDep(dingDingUser);
    }

    @Override
    public List<DingDingUser> selectAllUserId() {
        return mDingDingUserMapper.selectAllUserId();
    }

}
