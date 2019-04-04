package com.example.common.app;

import com.example.common.BuildConfig;

public class Common {
    /**
     * 一些不可变的永恒的参数
     * 通常用于一些配置
     */
    public interface Constance {

        //系统
        String MOBILE_TYPE = "android";
        String PHONE_ID = android.os.Build.SERIAL;

        String API_URL = "";

    String PUBLICK_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDByjEBIRpFIdwNDUTd1elH9WfenMwgh6u4x8Z0lauUhLN7cNHaE1aJYMRP1IRVOKEcl2mYeZlUopjxF21N1yOqLG9LgcF6e21MP2VO4OAKcvX+lK+vSpVmHyz51h59KPutqpMVWAyKjl1GgJHOtbhgJGyq/G4/bhTjeGU6Zk15FQIDAQAB";
}
    /**
     * SP文件配置
     */
    public interface SPApi {
        //SP文件名
        String FILE_NAME = "";
        //token
        String TOKEN = "TOKEN";
    }

    /**
     * 配置相关
     */
    public interface ConfigureRelevant {
        /**
         * 是否打印日志
         */
        boolean PRINT_LOGS = BuildConfig.DEBUG;
        /**
         * 网络链接超时时间
         */
        int CONNECT_TIME_OUT = 10;
    }
}
