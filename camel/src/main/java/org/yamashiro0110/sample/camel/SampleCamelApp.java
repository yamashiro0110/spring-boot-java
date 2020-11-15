package org.yamashiro0110.sample.camel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class SampleCamelApp {

    public static void main(final String[] args) {
        SpringApplication.run(SampleCamelApp.class, args);
    }

    /**
     * アプリケーションのプロセスが終了しないようにするため、Scheduledを設定する
     */
    @Scheduled(fixedRate = 60000)
    public void logging() {
        log.info("Fixed rate task - {}", System.currentTimeMillis() / 1000);
    }

}
