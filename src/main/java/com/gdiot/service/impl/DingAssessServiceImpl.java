package com.gdiot.service.impl;

import com.gdiot.entity.*;
import com.gdiot.mapper.DingAssessMapper;
import com.gdiot.service.IDingAssessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhouHR
 */
@Slf4j
@Service("DingAssessService")
public class DingAssessServiceImpl implements IDingAssessService {

    @Autowired
    private DingAssessMapper mDingAssessMapper;

    @Override
    public int insertDingAssess(DingAssessPo mDingAssessPo) {
        return mDingAssessMapper.insertDingAssess(mDingAssessPo);
    }

    @Override
    public int selectDingAssessCount() {
        return mDingAssessMapper.selectDingAssessCount();
    }

    @Override
    public List<DingAssessPo> selectDingAssess(String dd_id) {
        return mDingAssessMapper.selectDingAssess(dd_id);
    }

    @Override
    public int insertDingAssessDetail(DingAssessDetailPo mDingAssessDetailPo) {
        String assessId = mDingAssessDetailPo.getAssessId();
        List<DingAssessDetailPo> list = mDingAssessMapper.selectDingAssessById(assessId);
        if (list != null && list.size() > 0) {
            return mDingAssessMapper.updateDingAssessDetail(mDingAssessDetailPo);
        } else {
            return mDingAssessMapper.insertDingAssessDetail(mDingAssessDetailPo);
        }
    }

    @Override
    public List<DingAssessDetailPo> selectDingAssessDetailAgree(String beginTime, String endTime) {
        return mDingAssessMapper.selectDingAssessDetailAgree(beginTime, endTime);
    }

    @Override
    public int insertDingFileMediaId(DingFilePo mDingFilePo) {
        return mDingAssessMapper.insertDingFileMediaId(mDingFilePo);
    }

    @Override
    public List<DingFilePo> selectDingFileMediaId() {
        return mDingAssessMapper.selectDingFileMediaId();
    }

    @Override
    public int insertDingSendUser(DingSendUserPo mDingSendUserPo) {
        return mDingAssessMapper.insertDingSendUser(mDingSendUserPo);
    }

    @Override
    public List<DingSendUserPo> selectDingSendUser(String team) {
        return mDingAssessMapper.selectDingSendUser(team);
    }

    @Override
    public int updateDingSendUser(DingSendUserPo mDingSendUserPo) {
        return mDingAssessMapper.updateDingSendUser(mDingSendUserPo);
    }

    @Override
    public int insertDingSendLog(DingSendLogPo mDingSendLogPo) {
        return mDingAssessMapper.insertDingSendLog(mDingSendLogPo);
    }


}
