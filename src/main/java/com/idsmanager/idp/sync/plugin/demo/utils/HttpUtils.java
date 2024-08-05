package com.idsmanager.idp.sync.plugin.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.idsmanager.idp.sync.SCIMException;
import com.idsmanager.idp.sync.SyncErrorCode;
import com.idsmanager.micro.commons.web.filter.RIDHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @author ganyu
 * @date 2024/4/10 16:44
 */
public class HttpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    private final static Integer COMS_SUCCESS_CODE = 200;

    /**
     * 发送GET请求(http普通协议，兼容https协议)
     *
     * @param url 请求地址
     * @return 2015年11月4日
     */
    public static String sendGet(final String url, Map<String, String> params) throws SCIMException {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        String content = null;
        HttpGet httpGet = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            params.forEach(builder::setParameter);
            // 构建 URI 并创建 HttpGet 请求对象
            URI uri = builder.build();
            httpGet = new HttpGet(uri);
            //判断是否为https协议
            if (StringUtils.isNotBlank(url) && url.contains("https")) {
                httpClient = HttpsSSLClient.createSSLInsecureClient();
            } else {
                httpClient = HttpClients.custom().disableRedirectHandling().build();
            }
            httpGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpGet.addHeader("Accept-Encoding", "gzip,deflate,sdch");
            httpGet.addHeader("Content-Type", "text/html; charset=UTF-8");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.76 Safari/537.36");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                    .setSocketTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (COMS_SUCCESS_CODE.equals(statusCode)) {
                return EntityUtils.toString(response.getEntity());
            }else{
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSON.parseObject(responseBody);
                throw new SCIMException(SyncErrorCode.SCIM_ERR_REMOTE_ERROR_CODE, "调用COMS接口异常,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            LOG.error("[{}]- HttpGetUtil sendGet error, url is [{}]", RIDHolder.id(), url, e);
            throw new SCIMException(SyncErrorCode.SCIM_ERR_REMOTE_ERROR_CODE, "调用COMS接口异常,原因:" + e.getMessage());
        } finally {
            try {
                if (null != httpGet) {
                    httpGet.releaseConnection();
                }
                if (null != response) {
                    response.close();
                }
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                LOG.error("[{}]- HttpGetUtil sendGet finally error", RIDHolder.id(), e);
            }
        }
    }
}
