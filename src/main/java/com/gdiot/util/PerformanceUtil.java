package com.gdiot.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhouHR
 */
@Slf4j
public class PerformanceUtil {

    public static long spendTime(long start) {

        //long start=System.currentTimeMillis();
        //PerformanceUtil.spendTime(start);
        long end = System.currentTimeMillis();
        return end - start;

        //log.info("total spend time is "+(end-start));
    }

}
