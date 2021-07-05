package org.yamashiro0110.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PreDestroy;

@SpringBootApplication
@Slf4j
public class CacheApplication {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(CacheApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);
    }

    @PreDestroy
    public void end() {
        log.info("アプリケーションを終了します");
    }

}
