package com.gdiot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse.ProcessInstanceTopVo;
import com.dingtalk.api.response.OapiProcessinstanceListidsResponse.PageResult;
import com.dingtalk.api.response.OapiUserGetResponse.Roles;
import com.gdiot.entity.*;
import com.gdiot.jdbc.JdbcErpOPRView;
import com.gdiot.jdbc.JdbcErpZYView;
import com.gdiot.service.IDingAssessService;
import com.gdiot.service.IDingDepDataService;
import com.gdiot.service.IDingDingUserService;
import com.gdiot.util.*;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import com.taobao.api.internal.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 对接钉钉的工具类
 *
 * @author ZhouHR
 */
@Slf4j
public class DingDataAnalysis {

    private IDingDepDataService mIDingDepDataService;
    private IDingDingUserService mIDingDingUserService;
    private IDingAssessService mIDingAssessService;

    public String getToken() {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(DingUtils.tokenURL);
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(DingUtils.APPKEY);
            request.setAppsecret(DingUtils.APPSECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送工作通知
     *
     * @param userIdList
     * @param textMsg
     * @param accessToken
     * @return
     */
    public OapiMessageCorpconversationAsyncsendV2Response sendMessage(String userIdList, String textMsg, String accessToken) {
        try {

            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            request.setUseridList(userIdList);
            request.setAgentId(DingUtils.AGENTID);
            request.setToAllUser(false);

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setMsgtype("text");
            msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
            msg.getText().setContent(textMsg);
            request.setMsg(msg);

            OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, accessToken);
            System.out.println("getToken()-----" + response);

            return response;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        OapiMessageCorpconversationAsyncsendV2Response res = new OapiMessageCorpconversationAsyncsendV2Response();
        res.setCode("1");
        res.setBody("send msg error!");
        res.setErrcode(1L);
        res.setErrmsg("send msg error!");
        return res;
    }

    /**
     * 获取企业员工人数
     *
     * @param accessToken
     * @return
     */
    public Long getUserCount(String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_org_user_count");
            OapiUserGetOrgUserCountRequest request = new OapiUserGetOrgUserCountRequest();
            request.setOnlyActive(1L);
            request.setHttpMethod("GET");
            OapiUserGetOrgUserCountResponse response = client.execute(request, accessToken);
            return response.getCount();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * @param startTime
     * @param endTime
     * @param userId
     * @param accessToken
     * @return
     */
    public OapiProcessinstanceListidsResponse getPorcessListId(long startTime, long endTime, String userId, String accessToken) {
        try {

            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/listids");
            OapiProcessinstanceListidsRequest req = new OapiProcessinstanceListidsRequest();
            req.setProcessCode(DingUtils.processCode);
            req.setStartTime(startTime);
            req.setEndTime(endTime);
            req.setSize(10L);
            req.setCursor(0L);
            req.setUseridList(userId);
            OapiProcessinstanceListidsResponse response = client.execute(req, accessToken);
            System.out.println("response-----" + response);
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取审批实例ID列表
     *
     * @param processId
     * @param accessToken
     * @return
     */
    public OapiProcessinstanceGetResponse getPorcessInstance(String processId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
            request.setProcessInstanceId(processId);
            OapiProcessinstanceGetResponse response = client.execute(request, accessToken);
            System.out.println("response-----" + response);
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取部门详情
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public DingDepPo getDepDetail(String depId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
            OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
            request.setId(depId);
            request.setHttpMethod("GET");
            OapiDepartmentGetResponse response = client.execute(request, accessToken);

            System.out.print("getErrcode=" + response.getErrcode() + "/n");
            String body = response.getBody();
            DingDepPo mDingDepPo = new DingDepPo();
            mDingDepPo.setDep_id(depId);
            mDingDepPo.setDep_detail(body);
            mDingDepPo.setDep_name(response.getName());
            mDingDepPo.setParent_id(String.valueOf(response.getParentid()));

            mDingDepPo.setCode(response.getCode());
            mDingDepPo.setDeptManagerUseridList(response.getDeptManagerUseridList());
            mDingDepPo.setDeptPerimits(response.getDeptPerimits());
            mDingDepPo.setDeptPermits(response.getDeptPermits());
            mDingDepPo.setUserPerimits(response.getUserPerimits());
            mDingDepPo.setUserPermits(response.getUserPermits());

            return mDingDepPo;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取部门用户详情
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public String getDepUserDetail(long depId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
            OapiUserListbypageRequest request = new OapiUserListbypageRequest();
            //1L为根目录
            request.setDepartmentId(depId);
            request.setOffset(0L);
            request.setSize(10L);
            request.setOrder("entry_desc");
            request.setHttpMethod("GET");
            OapiUserListbypageResponse execute = client.execute(request, accessToken);
            System.out.println(execute);
            String list = execute.getBody();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取部门列表
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public String getDepList(String depId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
            OapiDepartmentListRequest request = new OapiDepartmentListRequest();
            //1根目录
            request.setId(depId);
            request.setHttpMethod("GET");
            OapiDepartmentListResponse response = client.execute(request, accessToken);
            String list = response.getBody();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户详情
     *
     * @param userId
     * @param accessToken
     * @return
     */
    public DingDingUser getUserDetail(String userId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);

            System.out.print("getErrcode=" + response.getErrcode() + "\n");

            long errCode = response.getErrcode();
            if (errCode == 0) {
                DingDingUser mDingDingUser = new DingDingUser();
                mDingDingUser.setDdId(response.getUserid());
                mDingDingUser.setName(response.getName());
                mDingDingUser.setEmail(response.getEmail());
                mDingDingUser.setDept1(String.valueOf(response.getDepartment()));
                mDingDingUser.setPosition(response.getPosition());
                mDingDingUser.setMobile(response.getMobile());

                //入职时间
                mDingDingUser.setStartWorkDate(String.valueOf(response.getHiredDate()));
                mDingDingUser.setWorkNo(response.getJobnumber());
                mDingDingUser.setPhone(response.getTel());
                mDingDingUser.setWorkLocation(response.getWorkPlace());
                mDingDingUser.setExtattr(response.getExtattr());
                mDingDingUser.setBody(response.getBody());

                //response.getDingId()
                mDingDingUser.setUserId(response.getUserid());
                mDingDingUser.setInviteMobile(response.getInviteMobile());
                mDingDingUser.setManagerUserId(response.getManagerUserId());
                mDingDingUser.setMessage(response.getMessage());
                mDingDingUser.setNickname(response.getNickname());
                mDingDingUser.setOpenId(response.getOpenId());
                mDingDingUser.setOrderInDepts(response.getOrderInDepts());
                mDingDingUser.setOrgEmail(response.getOrgEmail());

                mDingDingUser.setUnionid(response.getUnionid());

                String extattr = response.getExtattr();
                if (extattr != null) {
                    JSONObject bodyjson = JSONObject.parseObject(response.getBody());
                    if (bodyjson.containsKey("extattr")) {
                        JSONObject extjson = JSONObject.parseObject(bodyjson.getString("extattr"));
                        String erpNo = extjson.containsKey("ERP账号") ? extjson.getString("ERP账号") : null;
                        String erpUnionPay = extjson.containsKey("ERP联行号") ? extjson.getString("ERP联行号") : null;
                        String erpCompanyCode = extjson.containsKey("ERP公司代码") ? extjson.getString("ERP公司代码") : null;
                        String erpCompanyName = extjson.containsKey("ERP公司名称") ? extjson.getString("ERP公司名称") : null;
                        String erpBuCode = extjson.containsKey("ERP部门代码") ? extjson.getString("ERP部门代码") : null;
                        String erpBuName = extjson.containsKey("ERP部门名称") ? extjson.getString("ERP部门名称") : null;
                        String erpBankNo = extjson.containsKey("ERP报销银行卡号") ? extjson.getString("ERP报销银行卡号") : null;
                        String erpBankName = extjson.containsKey("ERP银行卡账户名称") ? extjson.getString("ERP银行卡账户名称") : null;
                        String erpRank = extjson.containsKey("ERP职级") ? extjson.getString("ERP职级") : null;
                        String erpIDCard = extjson.containsKey("ERP身份证号") ? extjson.getString("ERP身份证号") : null;
                        String ifPushMaycur = extjson.containsKey("是否传送每刻") ? extjson.getString("是否传送每刻") : "0";
                        mDingDingUser.setErpNo(erpNo);
                        mDingDingUser.setErpUnionPay(erpUnionPay);
                        mDingDingUser.setErpCompanyCode(erpCompanyCode);
                        mDingDingUser.setErpCompanyName(erpCompanyName);
                        mDingDingUser.setErpBuCode(erpBuCode);
                        mDingDingUser.setErpBuName(erpBuName);
                        mDingDingUser.setErpBankNo(erpBankNo);
                        mDingDingUser.setErpBankName(erpBankName);
                        mDingDingUser.setErpRank(erpRank);
                        mDingDingUser.setErpIDCard(erpIDCard);
                        mDingDingUser.setIfPushMaycur(ifPushMaycur);
                    }
                }

                List<Roles> rolesList = response.getRoles() != null ? response.getRoles() : null;
                if (rolesList != null) {
                    int count = rolesList.size();
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        list.add(rolesList.get(i).getName());
                    }
                    mDingDingUser.setRoles(list.toString());
                } else {
                    mDingDingUser.setRoles(null);
                }

                return mDingDingUser;
            } else if (errCode == 60121) {
                //找不到该用户 检查该企业下该员工是否存在
                return null;
            } else {
                return null;
            }

        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取部门用户userid列表
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public String getDepUserList(String depId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
            OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
            req.setDeptId(depId);
            req.setHttpMethod("GET");
            OapiUserGetDeptMemberResponse rsp = client.execute(req, accessToken);
            String list = rsp.getBody();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取子部门ID列表
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public String getSubDepList(String depId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
            OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
            //1根目录
            request.setId(depId);
            request.setHttpMethod("GET");
            OapiDepartmentListIdsResponse response = client.execute(request, accessToken);
            String list = response.getBody();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定部门的所有父部门ID
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public List<Long> getAllParentDepId(String depId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts_by_dept");
            OapiDepartmentListParentDeptsByDeptRequest request = new OapiDepartmentListParentDeptsByDeptRequest();
            request.setId(depId);
            request.setHttpMethod("GET");
            OapiDepartmentListParentDeptsByDeptResponse response = client.execute(request, accessToken);
            System.out.print("getErrcode=" + response.getErrcode() + "/n");
            List<Long> list = response.getParentIds();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定用户的所有父部门ID
     *
     * @param userId
     * @param accessToken
     * @return DingDingUser
     */
    public DingDingUser getUserParentDepId(String userId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts");
            OapiDepartmentListParentDeptsRequest request = new OapiDepartmentListParentDeptsRequest();
            request.setUserId(userId);
            request.setHttpMethod("GET");
            OapiDepartmentListParentDeptsResponse response = client.execute(request, accessToken);
            System.out.print("getCode=" + response.getCode() + "\n");
            System.out.print("getErrorCode=" + response.getErrorCode() + "\n");
            long errCode = response.getErrcode();
            String dep = response.getDepartment();
            String body = response.getBody();

            if (errCode == 0) {
                DingDingUser mDingDingUser = new DingDingUser();
                mDingDingUser.setDdId(userId);

                System.out.println("depList=" + dep);
                List<List<Long>> Alllist = new ArrayList<List<Long>>();
                JSONArray arr = JSONArray.parseArray(dep);
                for (Object obj : arr) {
                    String json = obj.toString();
                    JSONArray arr0 = JSONArray.parseArray(json);
                    int count = arr0.size();
                    ArrayList<Long> list = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        list.add(arr0.getLong(i));
                        System.out.println("depl00=" + arr0.getLong(i));
                    }
                    if (count == 6) {
                        mDingDingUser.setDept1(String.valueOf(arr0.getLong(4)));
                        mDingDingUser.setDept2(String.valueOf(arr0.getLong(3)));
                        mDingDingUser.setDept3(String.valueOf(arr0.getLong(2)));
                        mDingDingUser.setDept4(String.valueOf(arr0.getLong(1)));
                        mDingDingUser.setDept5(String.valueOf(arr0.getLong(0)));
                    }
                    if (count == 5) {
                        mDingDingUser.setDept1(String.valueOf(arr0.getLong(3)));
                        mDingDingUser.setDept2(String.valueOf(arr0.getLong(2)));
                        mDingDingUser.setDept3(String.valueOf(arr0.getLong(1)));
                        mDingDingUser.setDept4(String.valueOf(arr0.getLong(0)));
                    }
                    if (count == 4) {
                        mDingDingUser.setDept1(String.valueOf(arr0.getLong(2)));
                        mDingDingUser.setDept2(String.valueOf(arr0.getLong(1)));
                        mDingDingUser.setDept3(String.valueOf(arr0.getLong(0)));
                    }
                    if (count == 3) {
                        mDingDingUser.setDept1(String.valueOf(arr0.getLong(1)));
                        mDingDingUser.setDept2(String.valueOf(arr0.getLong(0)));
                    }
                    if (count == 2) {
                        mDingDingUser.setDept1(String.valueOf(arr0.getLong(0)));
                    }
                    Alllist.add(list);
                }
                return mDingDingUser;
            } else {
                return null;
            }

        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有部门下的用户详情，并保存数据库
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public JSONArray getAllDepUserDetail(String depId, String accessToken) {
        System.out.println("depId:" + depId + "\n");
        //获取根目录下所有的子部门列表
        String subDepList = getSubDepList(depId, accessToken);
        System.out.println("subDepList:" + subDepList + "\n");
        JSONArray jsonarr = new JSONArray();

        List<String> list = analysisDepList(subDepList);
        if (list != null && list.size() > 0) {
            //有子部门
            for (int i = 0; i < list.size(); i++) {
                String depId0 = list.get(i);

                //获取部门下的用户
                getDepUserDetailSave(depId0, accessToken);

                //获取子部门
                JSONArray subDepList0 = getAllDepUserDetail(depId0, accessToken);
                if (subDepList0 != null && subDepList0.size() > 0) {
                    jsonarr.add(subDepList0.toString());
                } else {
                    continue;
                }
            }
        } else {
            //无子部门，获取部门下用户
            getDepUserDetailSave(depId, accessToken);
            return null;
        }
        return jsonarr;
    }

    /**
     * 解析部门列表，返回部门列表list
     *
     * @param subDepList
     * @return
     */
    public List<String> analysisDepList(String subDepList) {
        List<String> list = new ArrayList<String>();
        list.clear();
        //解析子部门列表
        JSONObject depjson = JSONObject.parseObject(subDepList);
        if (depjson != null && depjson.containsKey("sub_dept_id_list")) {
            JSONArray sub_dept_id_list = depjson.getJSONArray("sub_dept_id_list");
            int sub_dept_id_list_size = sub_dept_id_list.size();
            for (int i = 0; i < sub_dept_id_list_size; i++) {
                long depId0 = sub_dept_id_list.getLong(i);
                String depIdStr = String.valueOf(depId0);
                list.add(depIdStr);
            }
        }

        return list;
    }

    /**
     * 取部门下的所有用户详情
     *
     * @param depId
     * @param accessToken
     */
    public void getDepUserDetailSave(String depId, String accessToken) {
        if (mIDingDingUserService == null) {
            mIDingDingUserService = SpringContextUtils.getBean(IDingDingUserService.class);
        }
        //获取指定部门ID的所有用户userID列表
        String depUserList = getDepUserList(depId, accessToken);
        System.out.println("depUserList: " + depUserList + "\n");

        List<String> list = analysisUserList(depUserList, accessToken);
        if (list != null && list.size() > 0) {
            //部门下有用户
            for (int i = 0; i < list.size(); i++) {
                String userID0 = list.get(i);

                //获取用户详情
                DingDingUser mDingDingUser = getUserDetail(userID0, accessToken);

                if (mDingDingUser != null) {
                    //获取用户的所有父部门列表
                    DingDingUser mDingDingUserdep = getUserParentDepId(userID0, accessToken);
                    mDingDingUser.setDept1(mDingDingUserdep.getDept1());
                    mDingDingUser.setDept2(mDingDingUserdep.getDept2());
                    mDingDingUser.setDept3(mDingDingUserdep.getDept3());
                    mDingDingUser.setDept4(mDingDingUserdep.getDept4());
                    mDingDingUser.setDept5(mDingDingUserdep.getDept5());
                    mIDingDingUserService.insertDingDingUser(mDingDingUser);
                } else {
                    continue;
                }
            }
        } else {
            //部门下无用户
        }
    }

    /**
     * 解析用户列表
     *
     * @param depUserList
     * @param accessToken
     * @return
     */
    public List<String> analysisUserList(String depUserList, String accessToken) {
        List<String> list = new ArrayList<String>();
        list.clear();
        JSONObject json = JSONObject.parseObject(depUserList);

        if (json != null && json.containsKey("userIds")) {
            JSONArray userIds = json.getJSONArray("userIds");

            System.out.println("userIds=" + userIds.toString() + "\n");
            int userIdCount = userIds.size();
            System.out.println("userIdCount=" + userIdCount + "\n");
            for (int i = 0; i < userIdCount; i++) {
                String userId0 = userIds.getString(i);
                System.out.println("userId0: " + userId0 + "\n");
                list.add(userId0);
            }
        }

        return list;
    }

    /**
     * 获取所有部门的详细信息
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public JSONArray getAllDepDetail(String depId, String accessToken) {
        System.out.println("depId:" + depId + "\n");
        if (mIDingDepDataService == null) {
            mIDingDepDataService = SpringContextUtils.getBean(IDingDepDataService.class);
        }
        //获取根目录下所有的子部门列表
        String subDepList = getSubDepList(depId, accessToken);
        JSONArray jsonarr = new JSONArray();

        List<String> list = analysisDepList(subDepList);
        if (list != null && list.size() > 0) {
            //有子部门
            for (int i = 0; i < list.size(); i++) {
                String depId0 = list.get(i);
                System.out.println("depId0: " + i + ":" + depId0 + "\n");

                //获取部门详情
                DingDepPo mDingDepPo = getDepDetail(depId0, accessToken);
                mIDingDepDataService.addOne(mDingDepPo);

                //获取下一层子部门
                JSONArray subDepList0 = getAllDepDetail(depId0, accessToken);
                if (subDepList0 != null && subDepList0.size() > 0) {
                    System.out.println("subDepList0: " + subDepList0 + "\n");
                    jsonarr.add(subDepList0.toString());
                } else {
                    continue;
                }
            }
        } else {
            ////无子部门,获取部门详情
            DingDepPo mDingDepPo = getDepDetail(depId, accessToken);
            mIDingDepDataService.addOne(mDingDepPo);
            return null;
        }
        return jsonarr;
    }

    /**
     * 获取所有用户父级部门
     *
     * @param accessToken
     */
    public void getAllUserParentDep(String accessToken) {
        if (mIDingDingUserService == null) {
            mIDingDingUserService = SpringContextUtils.getBean(IDingDingUserService.class);
        }
        List<DingDingUser> list = mIDingDingUserService.selectAllUserId();
        for (DingDingUser user : list) {
            String userId = user.getDdId();

            DingDingUser mDingDingUser = getUserParentDepId(userId, accessToken);
            if (mDingDingUser != null) {
                mIDingDingUserService.updateUserDep(mDingDingUser);
            } else {
                continue;
            }
        }
    }

    /**
     * 根据用户ID，获取指定时间段内的开票审批流
     *
     * @param startTime
     * @param endTime
     * @param userId
     * @param accessToken
     * @return id_list
     */
    public List<String> getAssessListId(long startTime, long endTime, String userId, String accessToken) {
        try {
            List<String> id_list = new ArrayList<>();
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/listids");
            OapiProcessinstanceListidsRequest req = new OapiProcessinstanceListidsRequest();
            req.setProcessCode(DingUtils.processCode);
            req.setStartTime(startTime);
            req.setEndTime(endTime);
            req.setSize(10L);
            req.setCursor(0L);
            req.setUseridList(userId);
            OapiProcessinstanceListidsResponse response = client.execute(req, accessToken);
            if (response != null) {
                long errCode = response.getErrcode();
                if (errCode == 0) {
                    log.info("getBody-----" + response.getBody() + "\n");
                    PageResult result = response.getResult();
                    id_list = result.getList();
                    log.info("id_list=" + id_list.toString());
                    if (id_list != null && id_list.size() > 0) {
                        return id_list;
                    }
                }
            }
            return id_list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据实例ID获取审批流
     *
     * @param processId
     * @param accessToken
     * @return DingAssessDetailPo
     */
    public DingAssessDetailPo getAssessInstance(String processId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
            request.setProcessInstanceId(processId);
            OapiProcessinstanceGetResponse response = client.execute(request, accessToken);
            long errCode = response.getErrcode();
            log.info("errCode-----" + errCode);

            DingAssessDetailPo mDingAssessDetailPo = new DingAssessDetailPo();
            if (errCode == 0) {
                mDingAssessDetailPo = AnalysisAssessInstance(processId, response);
                return mDingAssessDetailPo;
            } else {
                return null;
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有改造审批单的列表
     *
     * @param accessToken
     * @param params
     */
    public void getAllAssessList(String accessToken, Map<String, Object> params) {
        if (mIDingDingUserService == null) {
            mIDingDingUserService = SpringContextUtils.getBean(IDingDingUserService.class);
        }
        if (mIDingAssessService == null) {
            mIDingAssessService = SpringContextUtils.getBean(IDingAssessService.class);
        }

        long endTime = System.currentTimeMillis();
        long startTime = endTime - 24 * 60 * 60 * 1000;
        if (params != null) {
            if (params.containsKey("startTime")) {
                startTime = (long) params.get("startTime");
            }
            if (params.containsKey("endTime")) {
                endTime = (long) params.get("endTime");
            }
        }
        log.info("startTime-----" + DateUtil.milliSecond2Date(String.valueOf(startTime), "yyyy-MM-dd HH:mm:ss"));
        log.info("endTime-----" + DateUtil.milliSecond2Date(String.valueOf(endTime), "yyyy-MM-dd HH:mm:ss"));

//			JSONArray jsonArr = new JSONArray();
        List<DingDingUser> list = mIDingDingUserService.selectAllUserId();
        for (DingDingUser user : list) {
            String userId = null;
            try {
                userId = user.getDdId();
            } catch (Exception e) {
                log.info("e=" + e.toString());
            }
            if (userId != null) {
                log.info("userId-----" + userId);
                List<String> id_list = getAssessListId(startTime, endTime, userId, accessToken);
                log.info("id_list=" + id_list.toString());
                if (id_list != null && id_list.size() > 0) {
                    DingAssessPo mDingAssessPo = new DingAssessPo();
                    mDingAssessPo.setDd_id(userId);
                    mDingAssessPo.setAssess_list(id_list.toString());
                    mDingAssessPo.setStart_time(String.valueOf(startTime));
                    mDingAssessPo.setEnd_time(String.valueOf(endTime));
                    mIDingAssessService.insertDingAssess(mDingAssessPo);

                    int list_size = id_list.size();
                    for (int i = 0; i < list_size; i++) {
                        String assessId = id_list.get(i);
                        DingAssessDetailPo mDingAssessDetailPo = getAssessInstance(assessId, accessToken);
                        if (mDingAssessDetailPo != null) {
                            mIDingAssessService.insertDingAssessDetail(mDingAssessDetailPo);
                        }
                        String proj_code = mDingAssessDetailPo.getProjectCode();
                        String applyId = mDingAssessDetailPo.getBusinessCode();
                        String applyReasonType = mDingAssessDetailPo.getApplyReasonType();
                        String auditResult = mDingAssessDetailPo.getAuditResult();
                        String auditStatus = mDingAssessDetailPo.getAuditStatus();
                        if ("agree".equals(auditResult) && "COMPLETED".equals(auditStatus)) {
                            //审批通过的

                            int result = JdbcErpOPRView.selectORPByProjCode(proj_code, applyId, applyReasonType, auditResult);
                            int result2 = JdbcErpZYView.selectZYByProjCode(proj_code, applyId, applyReasonType, auditResult);
                        }
                    }
                } else {
                    continue;
                }

            } else {
                continue;
            }

        }
//			return jsonArr;
    }


    /**
     * 解析差旅数据表单
     *
     * @param list
     * @return
     */
    public DingAssessDetailPo analysisProcessFormValues(List<OapiProcessinstanceGetResponse.FormComponentValueVo> list) {
        System.out.println("-------------------------------analysis Process FormValues----------------------------------- " + "\n");
        DingAssessDetailPo mDingAssessDetailPo = new DingAssessDetailPo();
        try {
            if (list != null && list.size() > 0) {
                for (OapiProcessinstanceGetResponse.FormComponentValueVo vo : list) {
                    String name = vo.getName();
                    String value = vo.getValue();
                    String id = vo.getId();
                    log.info("name: " + name + "\n");
                    log.info("value: " + value + "\n");

                    if ("省公司".equals(name) || "TextField-K4MIBYDS".equals(id)) {
                        mDingAssessDetailPo.setProvinceName(value);
                    } else if ("申请事由类型".equals(name) || "DDMultiSelectField-K4MKUOJ7".equals(id)) {
                        //出差天数

                        //去掉头尾[" "]
                        String reValue = value.substring(2, value.length() - 2);
                        mDingAssessDetailPo.setApplyReasonType(reValue);
                    } else if ("地市".equals(name) || "TextField-K8R5ETLQ".equals(id)) {
                        mDingAssessDetailPo.setCityName(value);
                    } else if ("事业部".equals(name) || "TextField-K4MIBYDT".equals(id)) {
                        mDingAssessDetailPo.setDepBizName(value);
                    } else if ("申请人".equals(name) || "TextField-K4MKUOJ3".equals(id)) {
                        mDingAssessDetailPo.setApplicantName(value);
                    } else if ("联系电话".equals(name) || "TextField-K4MKUOJ4".equals(id)) {
                        mDingAssessDetailPo.setApplicantPhone(value);
                    } else if ("业务类型".equals(name) || "DDSelectField-K5F2Z7CD".equals(id)) {
                        mDingAssessDetailPo.setBusinessType(value);
                    } else if ("委托运营商".equals(name) || "DDSelectField-K8S3K1L1".equals(id)) {
                        mDingAssessDetailPo.setEntrustOperator(value);
                    } else if ("项目编号".equals(name) || "TextField-K3FK0BED".equals(id)) {
                        mDingAssessDetailPo.setProjectCode(value);
                    } else if (("项目名称").equals(name) || "TextField-K4NQY6PF".equals(id)) {
                        mDingAssessDetailPo.setProjectName(value);
                    } else if (("计划交付时间").equals(name) || "DDDateField-K4MKUOJ6".equals(id)) {
                        mDingAssessDetailPo.setDeliveryTime(value);
                    } else if (("运营商年租金").equals(name) || "MoneyField-K4MMD1SR".equals(id)) {
                        mDingAssessDetailPo.setOperatorRent(value);
                    } else if (("场租年租金").equals(name) || "MoneyField-K4MMD1SQ".equals(id)) {
                        mDingAssessDetailPo.setColocationRent(value);
                    } else if (("采购总成本").equals(name) || "MoneyField-K4MMD1SS".equals(id)) {
                        mDingAssessDetailPo.setProcurementCost(value);
                    } else if (("工程费").equals(name) || "MoneyField-K4MMD1ST".equals(id)) {
                        mDingAssessDetailPo.setProjectCost(value);
                    } else if (("情况描述").equals(name) || "TextField-K4MIBYDU".equals(id)) {
                        mDingAssessDetailPo.setMemo(value);
                    }
                }
            }
        } catch (JSONException e) {
            log.info("JSONException e=" + e);
        }
        System.out.println("-------------------------------analysis Process FormValues  end----------------------------------- " + "\n");
        return mDingAssessDetailPo;
    }

    /**
     * 解析body
     *
     * @param body
     * @return
     */
    public DingAssessPo analysisProcessBody(String body) {
        System.out.println("-------------------------------analysis Process Body----------------------------------- " + "\n");
        DingAssessPo mDingEvaluatePo = new DingAssessPo();
        try {
            JSONObject bodyJson = JSON.parseObject(body);
            if (bodyJson.containsKey("process_instance")) {
                JSONObject process_instance = bodyJson.getJSONObject("process_instance");
                JSONArray arr = process_instance.getJSONArray("form_component_values");
                if (arr != null && arr.size() > 0) {
                    int arr_size = arr.size();
                    for (int i = 0; i < arr_size; i++) {
                        JSONObject sub = arr.getJSONObject(i);
                        log.info("name: " + sub.getString("name") + "\n");
                        log.info("component_type: " + sub.getString("component_type") + "\n");
                        String component_type = sub.getString("component_type");
                        if ("MoneyField".equals(component_type)) {
                            //预估出差费用
                            log.info("value: " + sub.getString("value") + "\n");
                            log.info("ext_value: " + sub.getString("ext_value") + "\n");
                            String value = sub.getString("value");
                            BigDecimal mBigDecimal = new BigDecimal(new DecimalFormat("0").format(Double.valueOf(value)));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            log.info("JSONException e=" + e);
        }
        return mDingEvaluatePo;
    }

    /**
     * 解析实例详情
     *
     * @param processId
     * @param response
     * @return
     */
    public DingAssessDetailPo AnalysisAssessInstance(String processId, OapiProcessinstanceGetResponse response) {
        //实例详情
        ProcessInstanceTopVo vo = response.getProcessInstance();
        String body = response.getBody();
        log.info("getBody-----" + response.getBody() + "\n");

        //审批实例标题
        log.info("getTitle审批实例标题-----" + vo.getTitle());
        //开始时间
        log.info("getCreateTime开始时间-----" + vo.getCreateTime());
        //结束时间
        log.info("getFinishTime结束时间-----" + vo.getFinishTime());
        //发起人
        log.info("getOriginatorUserid发起人-----" + vo.getOriginatorUserid());
        //发起部门
        log.info("getOriginatorDeptId发起部门-----" + vo.getOriginatorDeptId());
        //审批状态，分为NEW（新创建）RUNNING（运行中）TERMINATED（被终止）COMPLETED（完成）
        log.info("getStatus审批状态-----" + vo.getStatus());
        //抄送人
        log.info("getCcUserids抄送人-----" + vo.getCcUserids());
        //结果，分为NONE（无），AGREE（同意），REFUSE（拒绝），REDIRECTED（转交）
        log.info("getResult结果-----" + vo.getResult());
        //审批实例业务编号
        log.info("getBusinessId审批实例业务编号-----" + vo.getBusinessId());
        //发起部门
        log.info("getOriginatorDeptName发起部门-----" + vo.getOriginatorDeptName());

        List<OapiProcessinstanceGetResponse.FormComponentValueVo> formList = vo.getFormComponentValues();
        //标签名
        log.info("formList.get(0).getName标签名-----" + formList.get(0).getName());
        String value = formList.get(0).getValue();
        OapiProcessinstanceGetResponse.FormComponentValueVo valueVo = formList.get(0);
        //钉钉ID
        String ddId = vo.getOriginatorUserid();
        //申请实例ID
        String assessId = processId;
        //	审批编号
        String businessCode = vo.getBusinessId();
        //结果，分为NONE（无），AGREE（同意），REFUSE（拒绝），REDIRECTED（转交）
        String auditResult = vo.getResult();
        //审批状态，分为NEW（新创建）RUNNING（运行中）TERMINATED（被终止）COMPLETED（完成）
        String auditStatus = vo.getStatus();
        String title = vo.getTitle();
        long timel = System.currentTimeMillis();

        DingAssessDetailPo mDingAssessDetailPo2 = analysisProcessFormValues(formList);
        mDingAssessDetailPo2.setBusinessCode(businessCode);
        mDingAssessDetailPo2.setAssessId(assessId);
        mDingAssessDetailPo2.setAuditResult(auditResult);
        mDingAssessDetailPo2.setAuditStatus(auditStatus);
        mDingAssessDetailPo2.setBusinessCode(businessCode);
        mDingAssessDetailPo2.setDdId(ddId);
        mDingAssessDetailPo2.setDetail(title);
        mDingAssessDetailPo2.setTime(String.valueOf(timel));
        mDingAssessDetailPo2.setBody(body);
        return mDingAssessDetailPo2;
    }

    /**
     * 上传文件到钉盘
     *
     * @param agentId
     * @param url
     * @param filesize
     * @param accessToken
     * @return OapiFileUploadSingleResponse
     */
    public OapiFileUploadSingleResponse UploadFileToDP(String agentId, String url, long filesize, String accessToken) {
        OapiFileUploadSingleRequest request = new OapiFileUploadSingleRequest();
        request.setFileSize(filesize);
        request.setAgentId(agentId);
        DingTalkClient client;
        try {
            client = new DefaultDingTalkClient("https://oapi.dingtalk.com/file/upload/single?" + WebUtils.buildQuery(request.getTextParams(), "utf-8"));
            // 必须重新new一个请求
            request = new OapiFileUploadSingleRequest();
            request.setFile(new FileItem(url));
            OapiFileUploadSingleResponse response = client.execute(request, accessToken);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 推送文件给用户
     *
     * @param agentId
     * @param userId
     * @param mediaId
     * @param fileName
     * @param accessToken
     * @return OapiCspaceAddToSingleChatResponse
     */
    public OapiCspaceAddToSingleChatResponse sendFileToUser(String agentId, String userId, String mediaId, String fileName, String accessToken) {
        try {
            OapiCspaceAddToSingleChatRequest request = new OapiCspaceAddToSingleChatRequest();
            request.setAgentId(agentId);
            request.setUserid(userId);
            request.setMediaId(mediaId);
            request.setFileName(fileName);
            DingTalkClient client;
            client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/add_to_single_chat?" + WebUtils.buildQuery(request.getTextParams(), "utf-8"));
            OapiCspaceAddToSingleChatResponse response = client.execute(request, accessToken);
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取企业下的自定义空间
     *
     * @param agentId
     * @param accessToken
     * @return
     */
    public OapiCspaceGetCustomSpaceResponse getAllSpace(String agentId, String accessToken) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/get_custom_space");
        OapiCspaceGetCustomSpaceRequest request = new OapiCspaceGetCustomSpaceRequest();
        request.setAgentId(agentId);
        request.setDomain("test");
        request.setHttpMethod("GET");
        try {
            OapiCspaceGetCustomSpaceResponse response = client.execute(request, accessToken);
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件上传到钉盘
     *
     * @param fileName
     */
    public void uploadFileToDP(String fileName) {
        log.info("fileName=" + fileName);
        String url = PropertiesUtil.getValue("path") + fileName;
        log.info("url=" + url);
        File file = new File(url);
        if (url != null && FileUtils.ifFileExists(file)) {
            long fileSize = FileUtils.getFileSize1(file);
            System.out.println("fileSize1=" + fileSize);

            if (fileSize != -1 && fileSize > 8582) {
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
                } else {
                    log.info("---------uploadFileToDP fail --mediaId error--");
                }
            } else {
                log.info("---------uploadFileToDP fail --file error--");
            }
        }
    }

    /**
     * 钉盘文件发送给用户
     */
    public void sendFileToUser() {
        if (mIDingAssessService == null) {
            mIDingAssessService = SpringContextUtils.getBean(IDingAssessService.class);
        }
        String name5g = PropertiesUtil.getValue("filename");
        String name_orp = PropertiesUtil.getValue("filename.orp");
        String name_zy = PropertiesUtil.getValue("filename.zy");
        String notifyUserId = PropertiesUtil.getValue("team.notify");

        List<DingFilePo> list = mIDingAssessService.selectDingFileMediaId();
        if (list != null && list.size() > 0) {
            for (DingFilePo filePo : list) {
                String fileName = filePo.getFileName();
                String mediaId = filePo.getMediaId();
                DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
                String accessToken = mDingDataAnalysis.getToken();
                String agentId = DingUtils.agent_id;
                log.info("AccessToken=" + accessToken);
                String team = PropertiesUtil.getValue("team.xxzx");
                if (fileName.contains(name5g)) {
                    //5g  建维采购
                    //team.jianwei
                    team = PropertiesUtil.getValue("team.jianwei");
                    List<DingSendUserPo> userlist = mIDingAssessService.selectDingSendUser(team);
                    if (userlist != null && userlist.size() > 0) {
                        for (DingSendUserPo po : userlist) {
                            String userId = po.getDdId();
                            String name = po.getName();
                            OapiCspaceAddToSingleChatResponse res = mDingDataAnalysis.sendFileToUser(agentId, userId, mediaId, fileName, accessToken);
                            log.info("body", res.getBody());
                            log.info("getErrcode", res.getErrcode());

                            DingSendLogPo mDingSendLogPo = new DingSendLogPo();
                            mDingSendLogPo.setDdId(userId);
                            mDingSendLogPo.setName(name);
                            mDingSendLogPo.setFileName(fileName);
                            mDingSendLogPo.setSendResult(String.valueOf(res.getErrcode()));
                            mIDingAssessService.insertDingSendLog(mDingSendLogPo);

                            if (res.getErrcode() == 0) {
                                //发通知
                                String textMsg = fileName + " 推送给" + name + "成功";
                                sendMessage(notifyUserId, textMsg, accessToken);
                                log.info(fileName + " 推送给" + name + "成功");
                            }
                        }
                    }
                }
                if (fileName.contains(name_orp) || fileName.contains(name_zy)) {
                    //orp //zy  市场
                    //team.shichang
                    team = PropertiesUtil.getValue("team.shichang");
                    List<DingSendUserPo> userlist = mIDingAssessService.selectDingSendUser(team);
                    if (userlist != null && userlist.size() > 0) {
                        for (DingSendUserPo po : userlist) {
                            String userId = po.getDdId();
                            String name = po.getName();
                            OapiCspaceAddToSingleChatResponse res = mDingDataAnalysis.sendFileToUser(agentId, userId, mediaId, fileName, accessToken);
                            System.out.printf("body", res.getBody());
                            System.out.printf("getErrcode", res.getErrcode());

                            DingSendLogPo mDingSendLogPo = new DingSendLogPo();
                            mDingSendLogPo.setDdId(userId);
                            mDingSendLogPo.setName(name);
                            mDingSendLogPo.setFileName(fileName);
                            mDingSendLogPo.setSendResult(String.valueOf(res.getErrcode()));
                            mIDingAssessService.insertDingSendLog(mDingSendLogPo);

                            if (res.getErrcode() == 0) {
                                //发通知
                                String textMsg = fileName + " 推送给" + name + "成功";
                                sendMessage(notifyUserId, textMsg, accessToken);
                                log.info(fileName + " 推送给" + name + "成功");
                            }
                        }
                    }
                }
            }
        }
    }

}


