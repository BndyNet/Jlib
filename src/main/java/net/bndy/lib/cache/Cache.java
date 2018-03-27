package net.bndy.lib.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cache {

    private static HashMap cacheMap = new HashMap();

    public synchronized static void put(String key, Object value) {
        CacheObject cache = new CacheObject(key, value, -1);
        putCacheObject(cache);
    }

    public synchronized static void put(String key, Object value, long duration) {
        CacheObject cache = new CacheObject(key, value, duration);
        putCacheObject(cache);
    }

    public static Boolean getBoolean(String key) {
        CacheObject cache = getCacheObject(key);
        if (cache != null) {
            return cache.getValue() == null ? null : (Boolean) cache.getValue();
        }
        return null;
    }

    public static Integer getInteger(String key) {
        CacheObject cache = getCacheObject(key);
        if (cache != null) {
            return cache.getValue() == null ? null : (Integer) cache.getValue();
        }
        return null;
    }

    public static Long getLong(String key) {
        CacheObject cache = getCacheObject(key);
        if (cache != null) {
            return cache.getValue() == null ? null : (Long) cache.getValue();
        }
        return null;
    }

    public static String getString(String key) {
        CacheObject cache = getCacheObject(key);
        if (cache != null) {
            return cache.getValue() == null ? null : cache.getValue().toString();
        }
        return null;
    }

    public static <T> T get(String key) {
        CacheObject cache = getCacheObject(key);
        if (cache != null) {
            return cache.getValue() == null ? null : (T) cache.getValue();
        }
        return null;
    }

    public synchronized static void remove(String key) {
        cacheMap.remove(key);
    }

    public synchronized static void clear() {
        cacheMap.clear();
    }

    public synchronized static int clearKeyStartsWith(String keyPrefix) {
        int result = 0;
        Iterator iterator = cacheMap.entrySet().iterator();
        String currentKey;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            currentKey = (String) entry.getKey();
            if (currentKey.indexOf(keyPrefix) == 0) {
                cacheMap.remove(currentKey);
                result++;
            }
        }
        return result;
    }


    public static int getSize() {
        Iterator iterator = cacheMap.entrySet().iterator();
        String currentKey;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            currentKey = (String) entry.getKey();
            getCacheObject(currentKey);
        }
        return cacheMap.size();
    }

    public static int getSizeKeyStartsWith(String keyPrefix) {
        int result = 0;
        Iterator iterator = cacheMap.entrySet().iterator();
        String currentKey;
        CacheObject currentValue;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            currentKey = (String) entry.getKey();
            currentValue = getCacheObject(currentKey);
            if (currentValue != null) {
                if (currentKey.indexOf(keyPrefix) == 0 && !currentValue.isExpired()) {
                    result++;
                }
            }
        }
        return result;
    }


    private synchronized static void putCacheObject(CacheObject cache) {
        cacheMap.put(cache.getKey(), cache);
    }

    private synchronized static CacheObject getCacheObject(String key) {
        CacheObject cache = (CacheObject) cacheMap.get(key);
        if (cache != null) {
            if (!cache.isExpired()) {
                return cache;
            } else {
                remove(key);
            }
        }
        return null;
    }

}
