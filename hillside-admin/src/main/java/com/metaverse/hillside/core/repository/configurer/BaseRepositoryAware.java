package com.metaverse.hillside.core.repository.configurer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Slf4j
@Configuration
public class BaseRepositoryAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // 解析X-Token获取其中用户，用Jpa注解创建人，最后修改人。
        return Optional.of("currentAuditor");
    }

}
