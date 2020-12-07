package com.gdiot.service;


import com.gdiot.entity.DingAccountPo;

import java.util.List;

/**
 * @author ZhouHR
 */
public interface IDingAccountService {
    /**
     * @param mDingAccountPo
     * @return
     */
    int insertOne(DingAccountPo mDingAccountPo);

    /**
     * @param client
     * @return
     */
    int countAccountList(String client);

    /**
     * @param client
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<DingAccountPo> selectAccountList(String client, int pageNo, int pageSize);
}
