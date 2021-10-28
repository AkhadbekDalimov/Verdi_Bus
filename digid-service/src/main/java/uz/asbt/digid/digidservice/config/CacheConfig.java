package uz.asbt.digid.digidservice.config;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;
import java.util.concurrent.TimeUnit;

@Component
public class CacheConfig implements JCacheManagerCustomizer {

    @Override
    public void customize(final CacheManager cacheManager) {
        cacheManager.createCache("countries", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 10)))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));
        cacheManager.createCache("regions", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 10)))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));
        cacheManager.createCache("docTypes", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 10)))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));
        cacheManager.createCache("sexes", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 10)))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));
        cacheManager.createCache("nations", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 10)))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));
        cacheManager.createCache("districts", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 10)))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));
    }
}
