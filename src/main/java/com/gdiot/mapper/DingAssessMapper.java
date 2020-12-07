package com.gdiot.mapper;

import com.gdiot.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 */
@Mapper
@Component
public interface DingAssessMapper {
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
    List<DingAssessPo> selectDingAssess(@Param("dd_id") String dd_id);

    /**
     * @param mDingAssessPo
     * @return
     */
    int insertDingAssessDetail(DingAssessDetailPo mDingAssessPo);

    /**
     * @param beginTime
     * @param endTime
     * @return
     */
    List<DingAssessDetailPo> selectDingAssessDetailAgree(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * @param mDingAssessPo
     * @return
     */
    int updateDingAssessDetail(DingAssessDetailPo mDingAssessPo);

    /**
     * @param assessId
     * @return
     */
    List<DingAssessDetailPo> selectDingAssessById(@Param("assessId") String assessId);

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
    List<DingSendUserPo> selectDingSendUser(@Param("team") String team);

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
