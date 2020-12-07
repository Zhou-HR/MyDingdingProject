package com.gdiot.service.impl;

import com.gdiot.entity.DingUserPo;
import com.gdiot.mapper.DingUserDataMapper;
import com.gdiot.service.IDingUserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhouHR
 */
@Slf4j
@Service("DingUserDataService")
public class DingUserDataServiceImpl implements IDingUserDataService {

    @Autowired
    private DingUserDataMapper mDingUserDataMapper;

    @Override
    public int addOne(DingUserPo mDingUserPo) {

        //查询是否有，有的话替换，无的话插入
        String user_id = mDingUserPo.getUser_id();
        List<DingUserPo> list = mDingUserDataMapper.selectOne(user_id);
        if (list != null && list.size() > 0) {//已存在
            return mDingUserDataMapper.updateDetail(mDingUserPo);
        } else {//不存在
            return mDingUserDataMapper.insertOne(mDingUserPo);
        }
    }

    @Override
    public List<DingUserPo> selectOne(String user_id) {
        return mDingUserDataMapper.selectOne(user_id);
    }

}
