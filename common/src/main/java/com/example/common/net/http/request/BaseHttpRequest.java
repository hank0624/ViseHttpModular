package com.example.common.net.http.request;

import android.text.TextUtils;

import com.example.common.net.common.ViseConfig;
import com.example.common.net.http.ViseHttp;
import com.example.common.net.http.api.ApiService;
import com.example.common.net.http.callback.ACallback;
import com.example.common.net.http.func.ApiFunc;
import com.example.common.net.http.func.ApiRetryFunc;
import com.example.common.net.http.mode.ApiHost;
import com.example.common.net.http.mode.CacheMode;
import com.example.common.net.http.mode.CacheResult;
import com.example.common.net.view.ILoading;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Description: 通用的请求基类
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 17/7/22 15:23.
 */
public abstract class BaseHttpRequest<R extends BaseHttpRequest> extends BaseRequest<R> {

    protected ApiService apiService;//通用接口服务
    protected String suffixUrl = "";//链接后缀
    protected int retryDelayMillis;//请求失败重试间隔时间
    protected int retryCount;//重试次数
    protected boolean isLocalCache;//是否使用本地缓存
    protected CacheMode cacheMode;//本地缓存类型
    protected String cacheKey;//本地缓存Key
    protected long cacheTime;//本地缓存时间
    protected Map<String, String> params = new LinkedHashMap<>();//请求参数
    protected ILoading iLoading; // 加载框

    public BaseHttpRequest() {
    }

    public BaseHttpRequest(String suffixUrl) {
        if (!TextUtils.isEmpty(suffixUrl)) {
            this.suffixUrl = suffixUrl;
        }
    }



    public <T> Observable<T> request(Type type) {
        generateGlobalConfig();
        generateLocalConfig();
        return execute(type);
    }

    public <T> Observable<CacheResult<T>> cacheRequest(Type type) {
        generateGlobalConfig();
        generateLocalConfig();
        return cacheExecute(type);
    }

    public <T> void request(ACallback<T> callback) {
        generateGlobalConfig();
        generateLocalConfig();
        execute(callback);
    }

    @Override
    protected void generateLocalConfig() {
        super.generateLocalConfig();
        if (httpGlobalConfig.getGlobalParams() != null) {
            params.putAll(httpGlobalConfig.getGlobalParams());
        }
        if (retryCount <= 0) {
            retryCount = httpGlobalConfig.getRetryCount();
        }
        if (retryDelayMillis <= 0) {
            retryDelayMillis = httpGlobalConfig.getRetryDelayMillis();
        }
        if (isLocalCache) {
            if (cacheKey != null) {
                ViseHttp.getApiCacheBuilder().cacheKey(cacheKey);
            } else {
                ViseHttp.getApiCacheBuilder().cacheKey(ApiHost.getHost());
            }
            if (cacheTime > 0) {
                ViseHttp.getApiCacheBuilder().cacheTime(cacheTime);
            } else {
                ViseHttp.getApiCacheBuilder().cacheTime(ViseConfig.CACHE_NEVER_EXPIRE);
            }
        }
        if (baseUrl != null && isLocalCache && cacheKey == null) {
            ViseHttp.getApiCacheBuilder().cacheKey(baseUrl);
        }
        apiService = retrofit.create(ApiService.class);
    }

    protected abstract <T> Observable<T> execute(Type type);

    protected abstract <T> Observable<CacheResult<T>> cacheExecute(Type type);

    protected abstract <T> void execute(ACallback<T> callback);

    protected <T> ObservableTransformer<ResponseBody, T> norTransformer(final Type type) {
        return new ObservableTransformer<ResponseBody, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseBody> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if(iLoading != null) {
                                    iLoading.showNetLoading();
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .map(new ApiFunc<T>(type))
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis))
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if(iLoading!=null) {
                                    iLoading.closeLoading();
                                    iLoading=null;
                                }
                            }
                        });
            }
        };
    }

    /**
     * 为网络请求添加加载框
     * @param iLoading
     * @return
     */
    public R load(ILoading iLoading) {
        if (null!=this.iLoading){
            this.iLoading=null;
        }
        this.iLoading = iLoading;
        return (R)this;
    }
    /**
     * 添加请求参数
     *
     * @param paramKey
     * @param paramValue
     * @return
     */
    public R addParam(String paramKey, String paramValue) {
        if (paramKey != null && paramValue != null) {
            this.params.put(paramKey, paramValue);
        }
        return (R) this;
    }

    /**
     * 添加请求参数
     *
     * @param params
     * @return
     */
    public R addParams(Map<String, String> params) {
        if (params != null) {
            this.params.putAll(params);
        }
        return (R) this;
    }


    /**
     * 添加请求参数
     * @param params object仅为基本数据类型
     * @return
     */
    public R addBasicParams(Map<String, Object> params) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            this.params.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return (R) this;
    }

    /**
     * 移除请求参数
     *
     * @param paramKey
     * @return
     */
    public R removeParam(String paramKey) {
        if (paramKey != null) {
            this.params.remove(paramKey);
        }
        return (R) this;
    }

    /**
     * 设置请求参数
     *
     * @param params
     * @return
     */
    public R params(Map<String, String> params) {
        if (params != null) {
            this.params = params;
        }
        return (R) this;
    }

    /**
     * 设置请求失败重试间隔时间（毫秒）
     *
     * @param retryDelayMillis
     * @return
     */
    public R retryDelayMillis(int retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return (R) this;
    }

    /**
     * 设置请求失败重试次数
     *
     * @param retryCount
     * @return
     */
    public R retryCount(int retryCount) {
        this.retryCount = retryCount;
        return (R) this;
    }

    /**
     * 设置是否进行本地缓存
     *
     * @param isLocalCache
     * @return
     */
    public R setLocalCache(boolean isLocalCache) {
        this.isLocalCache = isLocalCache;
        return (R) this;
    }

    /**
     * 设置本地缓存类型
     *
     * @param cacheMode
     * @return
     */
    public R cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return (R) this;
    }

    /**
     * 设置本地缓存Key
     *
     * @param cacheKey
     * @return
     */
    public R cacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
        return (R) this;
    }

    /**
     * 设置本地缓存时间(毫秒)，默认永久
     *
     * @param cacheTime
     * @return
     */
    public R cacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
        return (R) this;
    }

    public String getSuffixUrl() {
        return suffixUrl;
    }

    public int getRetryDelayMillis() {
        return retryDelayMillis;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public boolean isLocalCache() {
        return isLocalCache;
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public Map<String, String> getParams() {
        return params;
    }

}
