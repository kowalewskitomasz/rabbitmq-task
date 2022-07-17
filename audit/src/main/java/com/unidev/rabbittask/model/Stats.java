package com.unidev.rabbittask.model;

import java.util.Objects;

public class Stats {

    private int produced;
    private int processed;
    private int certified;
    private int discarded;

    public int getProduced() {
        return produced;
    }

    public void setProduced(int produced) {
        this.produced = produced;
    }

    public Stats withProduced(int produced) {
        this.produced = produced;
        return this;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public Stats withProcessed(int processed) {
        this.processed = processed;
        return this;
    }

    public int getCertified() {
        return certified;
    }

    public void setCertified(int certified) {
        this.certified = certified;
    }

    public Stats withCertified(int certified) {
        this.certified = certified;
        return this;
    }

    public int getDiscarded() {
        return discarded;
    }

    public void setDiscarded(int discarded) {
        this.discarded = discarded;
    }

    public Stats withDiscarded(int discarded) {
        this.discarded = discarded;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return produced == stats.produced && processed == stats.processed && certified == stats.certified && discarded == stats.discarded;
    }

    @Override
    public int hashCode() {
        return Objects.hash(produced, processed, certified, discarded);
    }

    @Override
    public String toString() {
        return "Stats{" +
                "produced=" + produced +
                ", processed=" + processed +
                ", certified=" + certified +
                ", discarded=" + discarded +
                '}';
    }
}
