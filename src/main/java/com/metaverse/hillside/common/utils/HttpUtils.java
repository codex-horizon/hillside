package com.metaverse.hillside.common.utils;

import okhttp3.*;

import java.io.IOException;
import java.util.*;

public class HttpUtils {

    public static OkHttpClient okHttpClient;

    /**
     * 构建form表单
     *
     * @param params 表单参数
     * @return 表单
     */
    public static FormBody builderFormBody(Map<String, String> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        params.forEach(formBodyBuilder::add);
        return formBodyBuilder.build();
    }

    /**
     * 构建post请求
     *
     * @param url      资源地址
     * @param formBody 表单参数
     * @return 构建请求
     */
    public static Request builderPostRequest(String url, FormBody formBody) {
        return new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
    }

    /**
     * 构建post请求
     *
     * @param url      资源地址
     * @param formBody 表单参数
     * @param headers  请求头参数
     * @return 构建请求
     */
    public static Request builderPostRequest(String url, FormBody formBody, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder();
        headers.forEach(builder::addHeader);
        return builder
                .url(url)
                .post(formBody)
                .build();
    }

    /**
     * 构建get请求
     *
     * @param url     资源地址
     * @param headers 请求头参数
     * @return 构建请求
     */
    public static Request builderGetRequest(String url, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder();
        headers.forEach(builder::addHeader);
        return builder
                .url(url)
                .get()
                .build();
    }

    /**
     * 执行http请求
     *
     * @param request 请求对象
     * @return 响应对象
     * @throws IOException IO异常
     */
    public static Response callExecuteRequest(Request request) throws IOException {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().build();
        }
        return okHttpClient.newCall(request).execute();
    }
}
