package com.cohortteam6.config;

import org.springframework.cache.CacheManager;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName = "reconx:type=ReconConfig", description = "ReconX Configuration MBean")
public class ReconConfig {

    private final CacheManager cacheManager;
    
    private volatile double priceTolerance = 0.05;
    private volatile boolean cachingEnabled = true;

    public ReconConfig(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @ManagedAttribute(description = "The price tolerance for reconciliation (0.0 to 1.0)")
    public double getPriceTolerance() {
        return priceTolerance;
    }

    @ManagedAttribute
    public void setPriceTolerance(double priceTolerance) {
        if (priceTolerance < 0.0 || priceTolerance > 1.0) {
            throw new IllegalArgumentException("Price tolerance must be between 0.0 and 1.0");
        }
        this.priceTolerance = priceTolerance;
    }

    @ManagedAttribute(description = "Is caching enabled")
    public boolean isCachingEnabled() {
        return cachingEnabled;
    }

    @ManagedAttribute
    public void setCachingEnabled(boolean cachingEnabled) {
        this.cachingEnabled = cachingEnabled;
    }

    @ManagedOperation(description = "Evict all entries from caches")
    public void clearCache() {
        cacheManager.getCacheNames().forEach(n -> cacheManager.getCache(n).clear());
    }
}
