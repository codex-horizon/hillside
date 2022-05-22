package com.metaverse.hillside.core.configurer.filter.register;

import com.metaverse.hillside.core.configurer.filter.TraceIdFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Arrays;

@Slf4j
@Configuration
public class TraceIdFilterRegister {

    @Bean
    public TraceIdFilter traceIdFilter() {
        return new TraceIdFilter();
    }

    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistrationBean() {
        return new FilterRegistrationBean<TraceIdFilter>() {{
            setFilter(traceIdFilter());
            setOrder(Ordered.HIGHEST_PRECEDENCE);
            setUrlPatterns(Arrays.asList("/*", "/**"));
        }};
    }


}
