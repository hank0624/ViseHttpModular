package com.example.common.net.http.request;


import com.example.common.net.http.ViseHttp;
import com.example.common.net.http.callback.ACallback;
import com.example.common.net.http.core.ApiManager;
import com.example.common.net.http.mode.CacheResult;
import com.example.common.net.http.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * @Description: Get请求
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 16:05
 */
public class GetRequest extends BaseHttpRequest<GetRequest> {
    public GetRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.get(suffixUrl, params).compose(this.<T>norTransformer(type));
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        return this.<T>execute(type).compose(ViseHttp.getApiCache().<T>transformer(cacheMode, type));
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
        if (super.tag != null) {
            ApiManager.get().add(super.tag, disposableObserver);
        }
        if (isLocalCache) {
            this.cacheExecute(getSubType(callback)).subscribe(disposableObserver);
        } else {
            this.execute(getType(callback)).subscribe(disposableObserver);
        }
    }
}
