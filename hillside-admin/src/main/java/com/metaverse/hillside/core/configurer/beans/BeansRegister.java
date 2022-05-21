package com.metaverse.hillside.core.configurer.beans;

import com.metaverse.hillside.common.converter.Converter;
import com.metaverse.hillside.common.converter.IConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Slf4j
@Configuration
public class BeansRegister {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            // 解析X-Token获取其中用户，用Jpa注解创建人，最后修改人。
            return Optional.of("currentAuditor");
        };
    }

    @Bean
    public IConverter converterBean() {
        return new Converter();
    }

}
