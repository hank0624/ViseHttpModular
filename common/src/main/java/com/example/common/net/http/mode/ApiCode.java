package com.example.common.net.http.mode;

/**
 * @Description: 网络通用状态码定义
 * @author: <a href="http://xiaoyaoyou1212.360doc.com">DAWI</a>
 * @date: 2016-12-30 18:11
 */
public class ApiCode {

    /**
     * 对应HTTP的状态码
     */
    public static class Http {
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int REQUEST_TIMEOUT = 408;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int BAD_GATEWAY = 502;
        public static final int SERVICE_UNAVAILABLE = 503;
        public static final int GATEWAY_TIMEOUT = 504;
    }

    /**
     * Request请求码
     */
    public static class Request {
        //未知错误
        public static final int UNKNOWN = 1000;
        //解析错误
        public static final int PARSE_ERROR = 1001;
        //网络错误
        public static final int NETWORK_ERROR = 1002;
        //协议出错
        public static final int HTTP_ERROR = 1003;
        //证书出错
        public static final int SSL_ERROR = 1005;
        //连接超时
        public static final int TIMEOUT_ERROR = 1006;
        //调用错误
        public static final int INVOKE_ERROR = 1007;
        //类转换错误
        public static final int CONVERT_ERROR = 1008;
    }

    /**
     * Request请求码
     */
    public static class ErrorMsg {
        public static final String UNAUTHORIZED = "401";
        public static final String FORBIDDEN = "403";
        public static final String NOT_FOUND = "访问的网络地址不存在";
        public static final String REQUEST_TIMEOUT = "网络超时";
        public static final String INTERNAL_SERVER_ERROR = "网络错误";
        public static final String BAD_GATEWAY = "502";
        public static final String SERVICE_UNAVAILABLE = "503";
        public static final String GATEWAY_TIMEOUT = "504";

        //未知错误
        public static final String UNKNOWN = "未知异常";
        //解析错误
        public static final String PARSE_ERROR = "数据解析错误";
        //网络错误
        public static final String NETWORK_ERROR = "网络错误";
        //协议出错
        public static final String HTTP_ERROR = "协议出错";
        //证书出错
        public static final String SSL_ERROR = "证书出错";
        //连接超时
        public static final String TIMEOUT_ERROR = "连接超时";
        //调用错误
        public static final String INVOKE_ERROR = "调用错误";
        //类转换错误
        public static final String CONVERT_ERROR = "类转换错误";


    }
}
