package org.yamashiro0110.sample;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.LongStream;

@Component
@Slf4j
public class CacheCommand {
    @Autowired
    private AppConfig appConfig;

    public void save(final Cache<String, CacheData> cache) {
        LongStream.range(0, this.appConfig.getCount()).parallel().forEach((i) -> {
            final String key = CacheData.cacheId(i);
            final CacheData data = new CacheData();
            cache.put(key, data);

            if (Objects.isNull(cache.get(key))) {
                log.debug("cacheが保存できませんでした {}", key);
            }
        });
    }

    public void get(final Cache<String, CacheData> cache) {
        final long cacheMissCount = LongStream.range(0, this.appConfig.getCount())
            .mapToObj(CacheData::cacheId)
            .filter(key -> Objects.isNull(cache.get(key)))
            .peek(key -> log.debug("cacheが取得できませんでした {}", key))
            .count();

        log.info("cacheがhitしなかった件数 {}", cacheMissCount);
    }

    public void saveAndGet(final Cache<String, CacheData> cache) {
        final StopWatch stopWatch = StopWatch.createStarted();

        this.save(cache);
        log.info("cacheの保存を実行しました {}", stopWatch);

        this.get(cache);
        log.info("cacheの取得を実行しました {}", stopWatch);
    }

}
