package org.yamashiro0110.sample.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AsyncService {

    @Async
    public void process(final int num) {
        log.info("処理を開始 num:{} sleep:{}", num, num);
        this.sleep(num);
        log.info("処理を終了 num:{} sleep:{}", num, num);
    }

    private void sleep(final int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (final InterruptedException e) {
            log.error("thread sleep time:{}", time, e);
        }
    }

}
