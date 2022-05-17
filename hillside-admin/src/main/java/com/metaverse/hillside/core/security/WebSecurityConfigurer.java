//package com.metaverse.hillside.core.security;
//
//import com.metaverse.hillside.core.env.EnvProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//@Configuration
//public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
//
//    private final EnvProperties envProperties;
//
//    WebSecurityConfigurer(final EnvProperties envProperties) {
//        this.envProperties = envProperties;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers(envProperties.getIgnoreAuthenticationPaths()).permitAll()
//                .anyRequest().authenticated();
//
//    }
//
//}
