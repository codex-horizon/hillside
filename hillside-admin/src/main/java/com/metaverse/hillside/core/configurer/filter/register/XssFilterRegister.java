package com.metaverse.hillside.core.configurer.filter.register;

import com.metaverse.hillside.core.configurer.filter.XssFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.Arrays;

@Slf4j
@Configuration
public class XssFilterRegister {

    @Bean
    public XssFilter xssFilter() {
        return new XssFilter();
    }

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        return new FilterRegistrationBean<XssFilter>() {{
            setFilter(xssFilter());
            setUrlPatterns(Arrays.asList("/*", "/**"));
            setDispatcherTypes(DispatcherType.REQUEST);
        }};
    }

}
