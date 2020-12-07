package com.gdiot.controller;

import com.dingtalk.api.response.OapiCspaceGetCustomSpaceResponse;
import com.dingtalk.api.response.OapiFileUploadSingleResponse;
import com.gdiot.entity.DingAssessDetailPo;
import com.gdiot.entity.DingAssessPo;
import com.gdiot.entity.DingFilePo;
import com.gdiot.jdbc.JdbcErpOPRAll;
import com.gdiot.jdbc.JdbcErpOPRView;
import com.gdiot.jdbc.JdbcErpZYAll;
import com.gdiot.jdbc.JdbcErpZYView;
import com.gdiot.mapper.DingAssessMapper;
import com.gdiot.service.AsyncService;
import com.gdiot.service.IDingAssessService;
import com.gdiot.task.DataSenderTask;
import com.gdiot.util.DingDataAnalysis;
import com.gdiot.util.DingUtils;
import com.gdiot.util.PropertiesUtil;
import com.gdiot.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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

}
