package com.gdiot.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.dingtalk.api.response.OapiUserGetResponse.Roles;
import com.gdiot.entity.DingDepPo;
import com.gdiot.entity.DingDingUser;
import com.gdiot.service.IDingDepDataService;
import com.gdiot.service.IDingDingUserService;
import com.gdiot.service.IDingUserDataService;
import com.taobao.api.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * 对接钉钉的工具类
 *
 * @author ZhouHR
 */
public class DingDataAnalysis {

    private IDingDepDataService mIDingDepDataService;
    private IDingDingUserService mIDingDingUserService;

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
                mDingDingUser.setStartWorkDate(String.valueOf(response.getHiredDate()));//入职时间
                mDingDingUser.setWorkNo(response.getJobnumber());
                mDingDingUser.setPhone(response.getTel());
                mDingDingUser.setWorkLocation(response.getWorkPlace());
                mDingDingUser.setExtattr(response.getExtattr());
                mDingDingUser.setBody(response.getBody());
                mDingDingUser.setUserId(response.getUserid());//response.getDingId()
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

}


