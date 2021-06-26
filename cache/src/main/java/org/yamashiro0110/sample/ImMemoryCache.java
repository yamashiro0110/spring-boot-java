package org.yamashiro0110.sample;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "cache-mode.in-memory")
@Slf4j
public class ImMemoryCache implements CommandLineRunner {
    @Autowired
    private CacheCommand cacheCommand;

    private CacheManager cacheManager(final String cacheName, final int cacheSize) {
        return CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerBuilder.persistence("tmp/" + cacheName))
            .withCache(cacheName, CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, CacheData.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(cacheSize, MemoryUnit.MB)
            ))
            .build(true);
    }

    private void runCommand(final int cacheSize) {
        final String cacheName = "memCache_" + cacheSize;
        log.info("memCacheのcommandを開始します {}", cacheName);

        try (final CacheManager cacheManager = this.cacheManager(cacheName, cacheSize)) {
            final Cache<String, CacheData> cache = cacheManager.getCache(cacheName, String.class, CacheData.class);
            this.cacheCommand.saveAndGet(cache);
        }

        log.info("memCacheのcommandを実行しました {}", cacheName);
    }

    @Override
    public void run(final String... args) {
        List.of(100, 500, 1000).forEach(this::runCommand);
    }

}
