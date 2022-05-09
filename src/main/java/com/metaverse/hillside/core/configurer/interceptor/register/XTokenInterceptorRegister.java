package com.metaverse.hillside.core.configurer.interceptor.register;

import com.metaverse.hillside.core.configurer.interceptor.XTokenInterceptor;
import com.metaverse.hillside.core.env.EnvProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class XTokenInterceptorRegister implements WebMvcConfigurer {

    private final EnvProperties envProperties;

    XTokenInterceptorRegister(final EnvProperties envProperties) {
        this.envProperties = envProperties;
    }

    @Bean
    XTokenInterceptor xTokenInterceptor() {
        return new XTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(xTokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(envProperties.getIgnoreAuthenticationPaths());
    }


}



