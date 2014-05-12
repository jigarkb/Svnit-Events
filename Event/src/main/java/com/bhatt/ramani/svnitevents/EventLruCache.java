package com.bhatt.ramani.svnitevents;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class EventLruCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public EventLruCache(int cacheSize) {
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return (value.getRowBytes() * value.getHeight()) / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }
}
