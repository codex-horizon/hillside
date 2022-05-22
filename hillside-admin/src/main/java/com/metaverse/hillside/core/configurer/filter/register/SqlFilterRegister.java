package com.metaverse.hillside.core.configurer.filter.register;

import com.metaverse.hillside.core.configurer.filter.SqlFilter;
import com.metaverse.hillside.core.configurer.filter.XssFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.Arrays;

@Slf4j
@Configuration
public class SqlFilterRegister {

    @Bean
    public SqlFilter sqlFilter() {
        return new SqlFilter();
    }

    @Bean
    public FilterRegistrationBean<SqlFilter> sqlFilterRegistrationBean() {
        return new FilterRegistrationBean<SqlFilter>() {{
            setFilter(sqlFilter());
            setUrlPatterns(Arrays.asList("/*", "/**"));
            setDispatcherTypes(DispatcherType.REQUEST);
        }};
    }

}
