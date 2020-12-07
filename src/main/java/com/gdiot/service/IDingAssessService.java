package com.gdiot.service;


import com.gdiot.entity.*;

import java.util.List;

/**
 * @author ZhouHR
 */
public interface IDingAssessService {
    /**
     * @param mDingAssessPo
     * @return
     */
    int insertDingAssess(DingAssessPo mDingAssessPo);

    /**
     * @return
     */
    int selectDingAssessCount();

    /**
     * @param dd_id
     * @return
     */
    List<DingAssessPo> selectDingAssess(String dd_id);

    /**
     * @param mDingAssessDetailPo
     * @return
     */
    int insertDingAssessDetail(DingAssessDetailPo mDingAssessDetailPo);

    /**
     * @param beginTime
     * @param endTime
     * @return
     */
    List<DingAssessDetailPo> selectDingAssessDetailAgree(String beginTime, String endTime);

    /**
     * @param mDingFilePo
     * @return
     */
    int insertDingFileMediaId(DingFilePo mDingFilePo);

    /**
     * @return
     */
    List<DingFilePo> selectDingFileMediaId();

    /**
     * @param mDingSendUserPo
     * @return
     */
    int insertDingSendUser(DingSendUserPo mDingSendUserPo);

    /**
     * @param team
     * @return
     */
    List<DingSendUserPo> selectDingSendUser(String team);

    /**
     * @param mDingSendUserPo
     * @return
     */
    int updateDingSendUser(DingSendUserPo mDingSendUserPo);

    /**
     * @param mDingSendLogPo
     * @return
     */
    int insertDingSendLog(DingSendLogPo mDingSendLogPo);

}
