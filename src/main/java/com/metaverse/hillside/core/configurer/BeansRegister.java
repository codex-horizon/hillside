package com.metaverse.hillside.core.configurer;

import com.metaverse.hillside.common.converter.Converter;
import com.metaverse.hillside.common.converter.IConverter;
import com.metaverse.hillside.core.configurer.filter.TraceIdFilter;
import com.metaverse.hillside.core.configurer.filter.XssAndSqlInjectorFilter;
import com.metaverse.hillside.core.configurer.listener.ApplicationContextListener;
import com.metaverse.hillside.core.configurer.listener.ApplicationSessionListener;
import com.metaverse.hillside.core.configurer.servlet.ApplicationHttpServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.DispatcherType;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Configuration
public class BeansRegister implements AuditorAware<String> {

    @Bean
    public IConverter converterBean() {
        return new Converter();
    }

    @Bean
    public TraceIdFilter traceIdFilter() {
        return new TraceIdFilter();
    }

    @Bean
    public XssAndSqlInjectorFilter xssAndSqlInjectorFilter() {
        return new XssAndSqlInjectorFilter();
    }

    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistrationBean() {
        return new FilterRegistrationBean<TraceIdFilter>() {{
            setFilter(traceIdFilter());
            setOrder(Ordered.HIGHEST_PRECEDENCE);
            setUrlPatterns(Arrays.asList("/*", "/**"));
        }};
    }

    @Bean
    public FilterRegistrationBean<XssAndSqlInjectorFilter> xssAndSqlInjectorFilterRegistrationBean() {
        return new FilterRegistrationBean<XssAndSqlInjectorFilter>() {{
            setFilter(xssAndSqlInjectorFilter());
//            setOrder(Ordered.HIGHEST_PRECEDENCE);
            setUrlPatterns(Arrays.asList("/*", "/**"));
            // 设置过滤器作用范围（可以配置多种，这里指定过滤请求资源）
            setDispatcherTypes(DispatcherType.REQUEST);
        }};
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        // 解析X-Token获取其中用户，用Jpa注解创建人，最后修改人。
        return Optional.of("currentAuditor");
    }

    @Bean
    public ApplicationContextListener applicationContextListener() {
        return new ApplicationContextListener();
    }

    @Bean
    public ApplicationSessionListener applicationSessionListener() {
        return new ApplicationSessionListener();
    }

    @Bean
    public ApplicationHttpServlet applicationHttpServlet() {
        return new ApplicationHttpServlet();
    }

    @Bean
    public ServletListenerRegistrationBean<ApplicationContextListener> applicationContextListenerRegistrationBean() {
        return new ServletListenerRegistrationBean<ApplicationContextListener>() {{
            setListener(applicationContextListener());
//            setOrder(Ordered.HIGHEST_PRECEDENCE);
        }};
    }

    @Bean
    public ServletListenerRegistrationBean<ApplicationSessionListener> applicationSessionListenerRegistrationBean() {
        return new ServletListenerRegistrationBean<ApplicationSessionListener>() {{
            setListener(applicationSessionListener());
//            setOrder(Ordered.HIGHEST_PRECEDENCE);
        }};
    }

    @Bean
    public ServletRegistrationBean<ApplicationHttpServlet> applicationHttpServletRegistrationBean() {
        return new ServletRegistrationBean<ApplicationHttpServlet>() {{
            setServlet(applicationHttpServlet());
            setUrlMappings(Collections.singletonList("/**"));
//            setOrder(Ordered.HIGHEST_PRECEDENCE);
        }};
    }
}
