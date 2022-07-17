package com.unidev.rabbittask.service;

import com.unidev.rabbittask.model.Stats;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AuditStatsService {

    private final AtomicInteger produced = new AtomicInteger();
    private final AtomicInteger processed = new AtomicInteger();
    private final AtomicInteger certified = new AtomicInteger();
    private final AtomicInteger discarded = new AtomicInteger();

    public void incrementProduced() {
        produced.incrementAndGet();
    }

    public void incrementProcessed() {
        processed.incrementAndGet();
    }

    public void incrementCertified() {
        certified.incrementAndGet();
    }

    public void incrementDiscarded() {
        discarded.incrementAndGet();
    }

    public int getProduced() {
        return produced.get();
    }

    public int getProcessed() {
        return processed.get();
    }

    public int getCertified() {
        return certified.get();
    }

    public int getDiscarded() {
        return discarded.get();
    }

    public Stats getAllStats() {
        return new Stats()
                .withProduced(getProduced())
                .withProcessed(getProcessed())
                .withCertified(getCertified())
                .withDiscarded(getDiscarded());
    }

    public void resetStats() {
        produced.set(0);
        processed.set(0);
        certified.set(0);
        discarded.set(0);
    }
}
