package org.yamashiro0110.sample;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("cache.config")
@Data
public class AppConfig {
    private long count = 30;
}
