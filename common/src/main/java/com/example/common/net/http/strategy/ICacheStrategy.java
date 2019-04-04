package com.example.common.net.http.strategy;


import com.example.common.net.http.core.ApiCache;
import com.example.common.net.http.mode.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * @Description: 缓存策略接口
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/31 14:21.
 */
public interface ICacheStrategy<T> {
    <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Type type);
}
