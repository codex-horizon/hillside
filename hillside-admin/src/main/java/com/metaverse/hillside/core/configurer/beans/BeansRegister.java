package com.metaverse.hillside.core.configurer.beans;

import com.metaverse.hillside.common.converter.Converter;
import com.metaverse.hillside.common.converter.IConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class BeansRegister {

    @Bean
    public IConverter converterBean() {
        return new Converter();
    }

}
