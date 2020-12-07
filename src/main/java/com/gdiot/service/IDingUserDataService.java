package com.gdiot.service;


import com.gdiot.entity.DingUserPo;

import java.util.List;

/**
 * @author ZhouHR
 */
public interface IDingUserDataService {
    /**
     * @param mXBEMDataPo
     * @return
     */
    int addOne(DingUserPo mXBEMDataPo);

    /**
     * @param user_id
     * @return
     */
    List<DingUserPo> selectOne(String user_id);
}
