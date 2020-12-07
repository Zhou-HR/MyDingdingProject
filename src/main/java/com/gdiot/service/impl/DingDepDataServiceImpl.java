package com.gdiot.service.impl;

import com.gdiot.entity.DingDepPo;
import com.gdiot.mapper.DingDepDataMapper;
import com.gdiot.service.IDingDepDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhouHR
 */
@Slf4j
@Service("DingDepDataService")
public class DingDepDataServiceImpl implements IDingDepDataService {

    @Autowired
    private DingDepDataMapper mDingDepDataMapper;

    @Override
    public int addOne(DingDepPo mDingDepPo) {

        //查询是否有，有的话替换，无的话插入
        String dep_id = mDingDepPo.getDep_id();
        List<DingDepPo> list = mDingDepDataMapper.selectOne(dep_id);
        if (list != null && list.size() > 0) {//已存在
            return mDingDepDataMapper.updateDetail(mDingDepPo);
        } else {//不存在
            return mDingDepDataMapper.insertOne(mDingDepPo);
        }
    }

    @Override
    public List<DingDepPo> selectOne(String user_id) {
        return mDingDepDataMapper.selectOne(user_id);
    }

}
