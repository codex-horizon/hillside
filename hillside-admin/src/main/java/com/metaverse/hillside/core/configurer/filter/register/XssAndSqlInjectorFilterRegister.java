package com.metaverse.hillside.core.configurer.filter.register;

import com.metaverse.hillside.core.configurer.filter.XssAndSqlInjectorFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.Collections;

@Slf4j
@Configuration
public class XssAndSqlInjectorFilterRegister {

    @Bean
    public XssAndSqlInjectorFilter xssAndSqlInjectorFilter() {
        return new XssAndSqlInjectorFilter();
    }

    @Bean
    public FilterRegistrationBean<XssAndSqlInjectorFilter> xssAndSqlInjectorFilterRegistrationBean() {
        return new FilterRegistrationBean<XssAndSqlInjectorFilter>() {{
            setFilter(xssAndSqlInjectorFilter());
            setUrlPatterns(Collections.singletonList("/**"));
            // 设置过滤器作用范围（可以配置多种，这里指定过滤请求资源）
            setDispatcherTypes(DispatcherType.REQUEST);
        }};
    }

}
