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
@ConditionalOnProperty(name = "cache-mode.off-heap")
@Slf4j
public class OffHeapCache implements CommandLineRunner {
    @Autowired
    private CacheCommand cacheCommand;

    private CacheManager cacheManager(final String cacheName, final int cacheSize) {
        return CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerBuilder.persistence("tmp/" + cacheName))
            .withCache(cacheName, CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, CacheData.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(cacheSize, MemoryUnit.MB)
            ))
            .build(true);
    }

    private void runCommand(final int cacheSize) {
        final String cacheName = "offHeapCache_" + cacheSize;
        log.info("offHeapCacheのcommandを開始します {}", cacheName);

        try (final CacheManager cacheManager = this.cacheManager(cacheName, cacheSize)) {
            final Cache<String, CacheData> cache = cacheManager.getCache(cacheName, String.class, CacheData.class);
            this.cacheCommand.saveAndGet(cache);
        }

        log.info("offHeapCacheのcommandを実行しました {}", cacheName);
    }

    @Override
    public void run(final String... args) {
        List.of(100, 500, 1000).forEach(this::runCommand);
    }

}
