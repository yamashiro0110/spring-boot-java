package org.yamashiro0110.sample.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableAsync
@Slf4j
public class ThreadPoolDemoApplication {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(ThreadPoolDemoApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);
    }

    @Autowired
    private AsyncService asyncService;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            log.info("アプリケーションを開始");
            this.threadStart();
        };
    }

    void threadStart() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            this.asyncService.process(i);
            log.info("処理を実行 i:{}", i);
        });
    }

    @PreDestroy
    void onExit() {
        log.info("アプリケーションを終了");
    }

    @Bean
    ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        return new TaskExecutorBuilder()
            .corePoolSize(3)
            .allowCoreThreadTimeOut(true)
            .keepAlive(Duration.ofSeconds(1))
            .awaitTermination(true)
            .awaitTerminationPeriod(Duration.ofSeconds(30))
            .build();
    }

}
