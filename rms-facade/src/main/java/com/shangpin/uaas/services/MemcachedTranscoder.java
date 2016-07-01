package com.shangpin.uaas.services;

import net.spy.memcached.CachedData;
import net.spy.memcached.transcoders.Transcoder;

/**
 * Created by Administrator on 2014/7/30.
 */
@SuppressWarnings("rawtypes")
class MemcachedTranscoder implements Transcoder{
    @Override
	public  boolean asyncDecode(CachedData d) {
        return false;
    }

    @Override
    public CachedData encode(Object o) {
        return null;
    }

    @Override
    public Object decode(CachedData d) {
        return null;
    }

    @Override
    public int getMaxSize() {
        return 0;
    }
}
