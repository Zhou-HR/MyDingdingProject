package com.gdiot.service.impl;

import com.gdiot.entity.DingAccountPo;
import com.gdiot.mapper.DingAccountMapper;
import com.gdiot.service.IDingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhouHR
 */
@Slf4j
@Service("DingAccountService")
public class DingAccountServiceImpl implements IDingAccountService {

    @Autowired
    private DingAccountMapper mDingAccountMapper;

    @Override
    public int insertOne(DingAccountPo mDingAccountPo) {
        return mDingAccountMapper.insertOne(mDingAccountPo);
    }

    @Override
    public int countAccountList(String client) {
        // TODO Auto-generated method stub
        return mDingAccountMapper.countAccountList(client);
    }

    @Override
    public List<DingAccountPo> selectAccountList(String client, int pageNo, int pageSize) {
        int count = mDingAccountMapper.countAccountList(client);
        log.info("selectbyDevId lora wm count=" + count);
        int limit = pageSize;
        int offset = (pageNo - 1) * pageSize;

        List<DingAccountPo> list = mDingAccountMapper.selectAccountList(client, limit, offset);
        return list;
    }

}
