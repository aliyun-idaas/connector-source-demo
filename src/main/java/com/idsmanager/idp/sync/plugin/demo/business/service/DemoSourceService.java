package com.idsmanager.idp.sync.plugin.demo.business.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.idsmanager.idp.sync.SCIMException;
import com.idsmanager.idp.sync.SyncErrorCode;
import com.idsmanager.idp.sync.core.infrastructure.source.SourceDataItem;
import com.idsmanager.idp.sync.plugin.demo.entity.source.DemoSourceAccountEntity;
import com.idsmanager.idp.sync.plugin.demo.entity.source.DemoSourceOrganizationEntity;
import com.idsmanager.idp.sync.plugin.demo.utils.HttpUtils;
import com.taobao.api.internal.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ganyu
 * @date 2024/6/21 16:34
 * 实现demo来源数据的拉取逻辑
 */
public class DemoSourceService {

    private String orgUrl = "http://demo.org.source.pull";

    private String userUrl = "http://demo.user.source.pull";

    public List<SourceDataItem> getOrgsByTime (String begainTime) throws SCIMException{
        List<SourceDataItem> items = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("begainTime",begainTime);
        try {
            String responseBody = HttpUtils.sendGet(orgUrl, params);
            if(StringUtils.areNotEmpty(responseBody)){
                JSONObject jsonObject = JSON.parseObject(responseBody);
                JSONArray jsonArray = jsonObject.getJSONObject(responseBody).getJSONObject("data").getJSONArray("records");
                for (int i = 0; i < jsonArray.size(); i++) {
                    DemoSourceOrganizationEntity entity = new DemoSourceOrganizationEntity();
                    entity.setOrgId(jsonArray.getJSONObject(i).getString("orgId"));
                    entity.setOrgName(jsonArray.getJSONObject(i).getString("orgName"));
                    entity.setParentOrgId(jsonArray.getJSONObject(i).getString("parentOrgId"));
                    items.add(entity);
                }
            }
            return items;
        } catch (Exception e) {
            throw new SCIMException(SyncErrorCode.SCIM_ERR_REMOTE_ERROR_CODE, "调用DEMO接口异常,原因:" + e.getMessage());
        }
    }

    public List<SourceDataItem> getUserByTime(String begainTime) throws SCIMException{
        List<SourceDataItem> items = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("begainTime",begainTime);
        try {
            String responseBody = HttpUtils.sendGet(orgUrl, params);
            if(StringUtils.areNotEmpty(responseBody)){
                JSONObject jsonObject = JSON.parseObject(responseBody);
                JSONArray jsonArray = jsonObject.getJSONObject(responseBody).getJSONObject("data").getJSONArray("records");
                for (int i = 0; i < jsonArray.size(); i++) {
                    DemoSourceAccountEntity entity = new DemoSourceAccountEntity();
                    entity.setUserId(jsonArray.getJSONObject(i).getString("id"));
                    entity.setPassword(jsonArray.getJSONObject(i).getString("password"));
                    entity.setPhone(jsonArray.getJSONObject(i).getString("phone"));
                    entity.setEmail(jsonArray.getJSONObject(i).getString("email"));
                    entity.setDisplayName(jsonArray.getJSONObject(i).getString("displayNmae"));
                    items.add(entity);
                }
            }
            return items;
        } catch (Exception e) {
            throw new SCIMException(SyncErrorCode.SCIM_ERR_REMOTE_ERROR_CODE, "调用DEMO接口异常,原因:" + e.getMessage());
        }
    }

    public List<SourceDataItem> getOrgsByParentCode(String parentDeptId, boolean b) {
        // 通过根组织获取组织数据
        List<SourceDataItem> items = new ArrayList<>();
        // 具体逻辑实现
        return items;
    }

    public List<SourceDataItem> getUserByOrgCode(String parentDeptId, boolean b) {
        // 通过根组织获取组织数据获取账户数据
        List<SourceDataItem> items = new ArrayList<>();
        // 具体逻辑实现
        return items;
    }
}
