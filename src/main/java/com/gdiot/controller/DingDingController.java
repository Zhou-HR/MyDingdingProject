package com.gdiot.controller;

import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.gdiot.entity.*;
import com.gdiot.mapper.DingDingUserMapper;
import com.gdiot.notify.NotifySendUtil;
import com.gdiot.service.*;
import com.gdiot.session.RequestProcess;
import com.gdiot.task.DataSenderTask;
import com.gdiot.util.DingDataAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhouHR
 */
@RestController
@RequestMapping("/dd")
public class DingDingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DingDingController.class);

    @Autowired
    private DingDingUserMapper dingDingUserMapper;

    @Autowired()
    private AsyncService asyncService;

    @Autowired()
    private IDingUserDataService mIDingUserDataService;

    @Autowired()
    private IDingDepDataService mIDingDepDataService;

    @Autowired()
    private IDingDingUserService mIDingDingUserService;

    @Autowired()
    private IDingAccountService mIDingAccountService;

}
