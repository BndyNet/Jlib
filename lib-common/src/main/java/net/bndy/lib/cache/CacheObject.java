package net.bndy.lib.cache;

import java.io.Serializable;

public class CacheObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String key;
    private Object value;
    private long durationMillis;
    private long cachedMillis;

    public CacheObject(String key, Object value, long duration) {
        this.key = key;
        this.value = value;
        this.durationMillis = duration;
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
        return durationMillis;
    }

    public void setDuration(long duration) {
        this.durationMillis = duration;
    }

    public boolean isExpired() {
        if (durationMillis > 0 && System.currentTimeMillis() > cachedMillis + durationMillis) {
            return true;
        }
        return false;
    }
}
