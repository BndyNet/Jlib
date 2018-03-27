package net.bndy.lib.cache;

import java.io.Serializable;

public class CacheObject implements Serializable {

    private String key;
    private Object value;
    private long duration;
    private long cachedMillis;

    public CacheObject(String key, Object value, long duration) {
        this.key = key;
        this.value = value;
        this.duration = duration;
        this.cachedMillis = System.currentTimeMillis();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        if (this.isExpired()) {
            return null;
        }
        return value;
    }

    public Object getOriginValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isExpired() {
        if (duration > 0 && System.currentTimeMillis() > cachedMillis + duration) {
            return true;
        }
        return false;
    }
}
