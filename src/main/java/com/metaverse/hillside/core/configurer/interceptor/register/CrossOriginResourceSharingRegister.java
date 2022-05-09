package com.metaverse.hillside.core.configurer.interceptor.register;

import com.metaverse.hillside.core.env.EnvProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域资源共享
 */
@Slf4j
@Configuration
public class CrossOriginResourceSharingRegister implements WebMvcConfigurer {

    private final EnvProperties envProperties;

    CrossOriginResourceSharingRegister(final EnvProperties envProperties) {
        this.envProperties = envProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                // 携带Cookie信息
                .allowCredentials(true)
                // 允许跨域的域名，可以用*表示允许任何域名使用（从IDEA中打开HTML页面默认端口：63342）
                .allowedOriginPatterns(envProperties.getOriginCorsPaths())
                // 允许任何方法
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                // 允许任何请求头
                .allowedHeaders(CorsConfiguration.ALL)
                // 暴露哪些原始请求头部信息，maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
                .exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
    }
}
