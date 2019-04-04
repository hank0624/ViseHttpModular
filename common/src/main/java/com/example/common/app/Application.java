package com.example.common.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.example.common.net.http.ViseHttp;
import com.example.common.net.http.interceptor.HttpLogInterceptor;
import com.example.common.utils.LoaderImage;
import com.example.common.tool.RxToast;
import com.example.common.tool.RxTool;
import com.example.common.utils.SPUtils;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ConnectionPool;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Application extends android.app.Application {
    private static Application instance;
    public boolean isActive; //全局变量时间控制
    private List<Activity> activities = new ArrayList<>();

    private static SPUtils mSPUtils;

    public static SPUtils getSPUtils(){
        return mSPUtils;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mSPUtils = new SPUtils();
        initLog();
        try {
            initNet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //工具类初始化
        RxTool.init(this);
//        initLeakCanary();
        registerLifeCycle();
        //图片加载框架
        LoaderImage.getLoader().init(this);
        //腾讯Bugly
//        CrashReport.initCrashReport(getApplicationContext(), "a65d09a88e", false);

    }


    /**
     * 数据库初始化
     */
    public static void setup() {
        // 初始化数据库
//        FlowManager.init(new FlowConfig.Builder(getInstance())
//                .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
//                .build());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)
        {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public Resources getResources() {//还原字体大小
        Resources res = super.getResources();
        Configuration configuration = res.getConfiguration();
        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
        return res;
    }


    /**
     * 内存泄漏监测
     */
    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
    }

    /**
     *日志初始化
     */
    private void initLog() {
        ViseLog.getLogConfig()
                .configAllowLog(Common.ConfigureRelevant.PRINT_LOGS)
                .configShowBorders(false);
        ViseLog.plant(new LogcatTree());
    }

    /**
     * 网络初始化
     */
    private void initNet() throws IOException {

        HashMap<String,String> map=new HashMap<String,String>();
        map.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        String token = getSPUtils().getString(Common.SPApi.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            map.put("access_token", token);
        }
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                .baseUrl(Common.Constance.API_URL)
                .globalHeaders(map)
                .connectTimeout(Common.ConfigureRelevant.CONNECT_TIME_OUT)
                .retryCount(0)
                .retryDelayMillis(0)
                .callAdapterFactory(RxJava2CallAdapterFactory.create())
                .connectionPool(new ConnectionPool())
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.BODY));
    }

    private void registerLifeCycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activities.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activities.remove(activity);
            }
        });
    }

    /**
     * 退出应用
     */
    public void finishAll(){
        for (Activity activity : activities) {
            activity.finish();
        }

        showAccountView(this);
    }

    /**
     * 是否登陆
     */
    public boolean  isLogin(){
        return !TextUtils.isEmpty(getSPUtils().getString(Common.SPApi.TOKEN));
    }

    protected void showAccountView(Context context){

    }

    /**
     * 外部获取单例
     *
     * @return Application
     */
    public static Application getInstance() {
        return instance;
    }

    /**
     * 获取缓存文件夹地址
     *
     * @return 当前APP的缓存文件夹地址
     */
    public static File getCacheDirFile() {
        return instance.getCacheDir();
    }


    /**
     * 显示一个Toast
     *
     * @param msg 字符串
     */
    public static void showToast(final String msg) {

        new ApplicationMainHandler(new WeakReference<>(instance.getApplicationContext()))
                .post(new Runnable() {
                    @Override
                    public void run() {
                        RxToast.normal(msg);
                    }
                });

    }

    /**
     * 显示一个Toast
     *
     * @param msgId 传递的是字符串的资源
     */
    public static void showToast(@StringRes int msgId) {
        showToast(instance.getString(msgId));
    }


    private static class  ApplicationMainHandler extends Handler {
        private Context context;
        private ApplicationMainHandler(WeakReference<Context> weakContext){
            context=weakContext.get();
        }

    }

}
