package com.metaverse.hillside.core.env;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:env.properties")
@ConfigurationProperties(prefix = "env", ignoreUnknownFields = false)
public class EnvProperties {

    private String[] ignoreAuthenticationPaths;

    private String[] originCorsPaths;

    private String jwtSignatureSecretKey;

}