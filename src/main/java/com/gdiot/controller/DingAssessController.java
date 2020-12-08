package com.gdiot.controller;

import com.dingtalk.api.response.OapiCspaceGetCustomSpaceResponse;
import com.dingtalk.api.response.OapiFileUploadSingleResponse;
import com.gdiot.entity.DingAssessDetailPo;
import com.gdiot.entity.DingAssessPo;
import com.gdiot.entity.DingFilePo;
import com.gdiot.mapper.DingAssessMapper;
import com.gdiot.service.AsyncService;
import com.gdiot.service.IDingAssessService;
import com.gdiot.task.DataSenderTask;
import com.gdiot.util.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhouHR
 */
@Slf4j
@RestController
@RequestMapping("/assess")
public class DingAssessController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DingAssessController.class);

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private DingAssessMapper mDingAssessMapper;

    @Autowired
    private IDingAssessService mIDingAssessService;

    /**
     * 测试用接口：获取审批实例列表
     *
     * @return String
     */
    @RequestMapping("/getAssessListId")
    public String getAssessListId(@RequestBody Map<String, String> params) {
        String userId = null;
        long startTime = 0;
        long endTime = 0;
        if (params != null) {

            if (params.containsKey("userId")) {
                userId = params.get("userId");
            }
            if (params.containsKey("startTime")) {
                startTime = Long.parseLong(params.get("startTime"));
            }
            if (params.containsKey("endTime")) {
                endTime = Long.parseLong(params.get("endTime"));
            }
        }
        System.out.println(startTime);
        DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
        String accessToken = mDingDataAnalysis.getToken();
        System.out.println("accessToken-----" + accessToken + "\n");

        List<String> id_list = mDingDataAnalysis.getAssessListId(startTime, endTime, userId, accessToken);
        System.out.println("id_list=" + id_list.toString());

        if (id_list != null && id_list.size() > 0) {

            DingAssessPo mDingAssessPo = new DingAssessPo();
            mDingAssessPo.setDd_id(userId);
            mDingAssessPo.setAssess_list(id_list.toString());
            mDingAssessPo.setStart_time(String.valueOf(startTime));
            mDingAssessPo.setEnd_time(String.valueOf(endTime));
            mDingAssessMapper.insertDingAssess(mDingAssessPo);

            int list_size = id_list.size();
            for (int i = 0; i < list_size; i++) {
                String processId = id_list.get(i);
                System.out.println("processId=" + processId);
            }

            return id_list.toString();
        } else {
            return "获取到出差列表为空";
        }

    }

    /**
     * 测试用接口：获取审批实例
     *
     * @return
     */
    @RequestMapping("/getAssessInstance")
    public String getAssessInstance(@RequestBody Map<String, String> params) {
        String processId = null;
        if (params != null) {

            if (params.containsKey("processId")) {
                processId = params.get("processId");
            }
        }

        DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
        String accessToken = mDingDataAnalysis.getToken();
        System.out.println("AccessToken=" + accessToken);

        DingAssessDetailPo mDingAssessDetailPo = mDingDataAnalysis.getAssessInstance(processId, accessToken);
        if (mDingAssessDetailPo != null) {
            mIDingAssessService.insertDingAssessDetail(mDingAssessDetailPo);
        }

        LOGGER.info("getAssessInstance end-------------");
        return mDingAssessDetailPo.getBody();
    }

    /**
     * 测试用接口：---------------获取实例列表--------------
     *
     * @return
     */
    @RequestMapping("/getAllAssessList")
    public String getAllAssessList(@RequestBody Map<String, String> params) {
        //默认获取最近一天的出差数据，当前时间的前24小时
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 24 * 60 * 60 * 1000;
        Map<String, Object> map = new HashMap<>();
        if (params != null) {
            if (params.containsKey("startTime")) {
                startTime = Long.parseLong(params.get("startTime"));
            }
            if (params.containsKey("endTime")) {
                endTime = Long.parseLong(params.get("endTime"));
            }
        }
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        DataSenderTask task = new DataSenderTask(map, "all_assess_list");
        task.setAsyncService(asyncService);
        asyncService.executeAsync(task);

        return "ok";
    }

    /**
     * @param params
     * @return
     */
    @RequestMapping("/sqlToExcel")
    public String sqlToExcel(@RequestBody Map<String, String> params) {
        //默认获取数据，当前时间的前7天
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 7 * 24 * 60 * 60 * 1000;
        String endTimes = String.valueOf(endTime);
        String startTimes = String.valueOf(startTime);
        String type = "assess_file";
        if (params != null) {
            if (params.containsKey("type")) {
                type = params.get("type");
            }
            if (params.containsKey("startTime")) {
                startTimes = params.get("startTime");
            }
            if (params.containsKey("endTime")) {
                endTimes = params.get("endTime");
            }
        }

        Date date = new Date(System.currentTimeMillis());
        String dateT = new SimpleDateFormat("yyyy-MM-dd").format(date);

        String fileName = "";
        if ("assess_file".equals(type)) {
            fileName = PropertiesUtil.getValue("filename") + "-" + dateT + ".xlsx";
        }
        if ("assess_file_orp".equals(type)) {
            fileName = PropertiesUtil.getValue("filename.orp") + "-" + dateT + ".xlsx";
        }
        if ("assess_file_zy".equals(type)) {
            fileName = PropertiesUtil.getValue("filename.zy") + "-" + dateT + ".xlsx";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTimes);
        map.put("endTime", endTimes);
        map.put("fileName", fileName);
        map.put("type", type);
        log.info("fileName=" + fileName);

        DataSenderTask task = new DataSenderTask(map, type);
        task.setAsyncService(asyncService);
        asyncService.executeAsync(task);
        return "ok";
    }

    /**
     * 测试用接口：-------------39服务器上传钉盘-------------------
     *
     * @return
     */
    @RequestMapping("/uploadFileToDP")
    public String uploadFileToDP(@RequestBody Map<String, String> params) {
        String type = "assess_file";
        Map<String, Object> map = new HashMap<>();
        if (params != null) {
            if (params.containsKey("type")) {
                type = params.get("type");
            }
        }
        Date date = new Date(System.currentTimeMillis());
        String dateT = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String fileName = "";
        if ("assess_file".equals(type)) {
            fileName = PropertiesUtil.getValue("filename") + "-" + dateT + ".xlsx";
        }
        if ("assess_file_orp".equals(type)) {
            fileName = PropertiesUtil.getValue("filename.orp") + "-" + dateT + ".xlsx";
        }
        if ("assess_file_zy".equals(type)) {
            fileName = PropertiesUtil.getValue("filename.zy") + "-" + dateT + ".xlsx";
        }

        System.out.println("fileName=" + fileName);
        String path = PropertiesUtil.getValue("path");
        String url = path + fileName;
        System.out.println("url=" + url);
        File file = new File(url);

        long fileSize = FileUtils.getFileSize1(file);
        System.out.println("fileSize1=" + fileSize);

        if (url != null && fileSize != -1 && fileSize > 8582) {
            DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
            String accessToken = mDingDataAnalysis.getToken();
            String agentId = DingUtils.agent_id;
            System.out.println("AccessToken=" + accessToken);

            OapiFileUploadSingleResponse res = mDingDataAnalysis.UploadFileToDP(agentId, url, fileSize, accessToken);

            System.out.printf("getErrcode", res.getErrcode());
            long errCode = res.getErrcode();
            if (errCode == 0) {
                String mediaId = res.getMediaId();
                System.out.printf("body", res.getBody());
                System.out.printf("getMediaId", res.getMediaId());

                DingFilePo mDingFilePo = new DingFilePo();
                mDingFilePo.setFileName(fileName);
                mDingFilePo.setFileSize(String.valueOf(fileSize));
                mDingFilePo.setMediaId(mediaId);
                mDingFilePo.setTime(String.valueOf(System.currentTimeMillis()));
                if (mIDingAssessService == null) {
                    mIDingAssessService = SpringContextUtils.getBean(IDingAssessService.class);
                }
                mIDingAssessService.insertDingFileMediaId(mDingFilePo);

                log.info("--------------------------uploadFileToDP end ------------------------");
                return mediaId;
            } else {
                log.info("---------uploadFileToDP fail --mediaId error--");
            }
        } else {
            log.info("---------uploadFileToDP fail --file error--");
        }
        return "error";
    }

    /**
     * 测试用接口：-------------39服务器上传钉盘,之后获得mediaID发送文件给用户------------------
     *
     * @return
     */
    @RequestMapping("/sendFileToUser")
    public String sendFileToUser(@RequestBody Map<String, String> params) {

        DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
        mDingDataAnalysis.sendFileToUser();
        return "ok";
    }

    /**
     * 获取创建的自定义空间
     *
     * @param params
     * @return
     */
    @RequestMapping("/getAllSpace")
    public String getAllSpace(@RequestBody Map<String, String> params) {

        DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
        String accessToken = mDingDataAnalysis.getToken();
        String agentId = DingUtils.agent_id;
        System.out.println("AccessToken=" + accessToken);

        OapiCspaceGetCustomSpaceResponse res = mDingDataAnalysis.getAllSpace(agentId, accessToken);

        System.out.printf("body", res.getBody());
        System.out.printf("getErrcode", res.getErrcode());
        System.out.printf("getSpaceid", res.getSpaceid());
        return res.getBody();
    }

}
