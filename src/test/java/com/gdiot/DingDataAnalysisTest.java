package com.gdiot;

import com.gdiot.util.DingDataAnalysis;
import org.junit.Test;

/**
 * @author ZhouHR
 * @date 2020/12/8
 */
public class DingDataAnalysisTest {
    @Test
    public void testGetAssessListId() {
        String userId = null;
        long startTime = 0;
        long endTime = 0;
        DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
        String accessToken = mDingDataAnalysis.getToken();
        System.out.println("AccessToken=" + accessToken);
        startTime = 1599148800000L;
        endTime = 1607394737000L;
        System.out.println(mDingDataAnalysis.getAssessListId(startTime, endTime, userId, accessToken));

    }
}
