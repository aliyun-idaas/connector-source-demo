/*
 * Copyright (c) 2016 BeiJing JZYT Technology Co. Ltd
 * www.idsmanager.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * BeiJing JZYT Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with BeiJing JZYT Technology Co. Ltd.
 */
package com.idsmanager.idp.sync.plugin.demo.utils;

import com.idsmanager.micro.commons.web.filter.RIDHolder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 构建httpClient工具类
 *
 * @author mayintao
 * @date 2019-07-25
 */
public class HttpsSSLClient {

    private static final Logger LOG = LoggerFactory.getLogger(HttpsSSLClient.class);

    public static CloseableHttpClient instance = null;

    /**
     * 获取Https 请求客户端
     *
     * @return
     */
    public static CloseableHttpClient createSSLInsecureClient() {
        SSLContext sslcontext = createSSLContext();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new HostnameVerifier() {
            @Override
            public boolean verify(String paramString, SSLSession paramSSLSession) {
                return true;
            }
        });
        CloseableHttpClient httpclient = HttpClients.custom().disableRedirectHandling().setSSLSocketFactory(sslsf).build();
        return httpclient;
    }

    /**
     * 获取初始化SslContext
     *
     * @return
     */
    private static SSLContext createSSLContext() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
        } catch (Exception e) {
            LOG.error("[{}]- createSSLContext error", RIDHolder.id(), e);
        }
        return sslcontext;
    }

    /**
     * 自定义静态私有类
     */
    private static class TrustAnyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

}