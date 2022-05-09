package com.metaverse.hillside.core.configurer.beans;

import com.metaverse.hillside.core.configurer.listener.ApplicationContextListener;
import com.metaverse.hillside.core.configurer.listener.ApplicationSessionListener;
import com.metaverse.hillside.core.configurer.servlet.ApplicationHttpServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Slf4j
@Configuration
public class CoreBeansRegister {

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
        }};
    }

    @Bean
    public ServletListenerRegistrationBean<ApplicationSessionListener> applicationSessionListenerRegistrationBean() {
        return new ServletListenerRegistrationBean<ApplicationSessionListener>() {{
            setListener(applicationSessionListener());
        }};
    }

    @Bean
    public ServletRegistrationBean<ApplicationHttpServlet> applicationHttpServletRegistrationBean() {
        return new ServletRegistrationBean<ApplicationHttpServlet>() {{
            setServlet(applicationHttpServlet());
            setUrlMappings(Collections.singletonList("/**"));
        }};
    }

}
