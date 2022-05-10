package com.metaverse.hillside.core.helper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class RequestHelper {

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

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
        return okHttpClient.newCall(request).execute();
    }
}
