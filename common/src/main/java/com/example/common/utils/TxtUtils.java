package com.example.common.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.common.app.Application;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/5/11.
 */

public class TxtUtils {

    /**
     * 校验身份证
     */
    public static boolean chickedIdCard(String IDNumber) {


        String reg18 = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";

        return Pattern.matches(reg18, IDNumber);


    }

    /**
     * 校验电话号码
     */
    public static boolean chickedPhone(String phone) {

//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^[1](([3][0-9])|([4][5,7,9])|([5][4,6,9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$");

        Matcher m = p.matcher(phone);

        return m.matches();
    }


    /**
     * 校验邮箱
     */
    public static boolean chickedEmail(String email) {

        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配
        return m.matches();
    }


    /**
     * 从assert中获取一个字符串
     */
    public String getStringFromAssert(Context context, String fileName) {
        String content = null; // 结果字符串
        try {
            InputStream is = context.getResources().getAssets().open(fileName); // 打开文件
            int ch = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream(); // 实现了一个输出流
            while ((ch = is.read()) != -1) {
                out.write(ch); // 将指定的字节写入此 byte 数组输出流
            }
            byte[] buff = out.toByteArray();// 以 byte 数组的形式返回此输出流的当前内容
            out.close(); // 关闭流
            is.close(); // 关闭流
            content = new String(buff, "UTF-8"); // 设置字符串编码
        } catch (Exception e) {
            Toast.makeText(context, "对不起，没有找到指定文件！", Toast.LENGTH_SHORT).show();
        }
        return content;
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardNum
     * @return
     */
    public static boolean checkBankCard(String cardNum) {
        if (cardNum == null || cardNum.length() < 15 || cardNum.length() > 19) {
            Application.showToast("请填写正确的银行卡号");
            return false;
        } else {
            char bit = getBankCardCheckCode(cardNum.substring(0, cardNum.length() - 1));
            if (bit == 'N') {
                return false;
            }
            return cardNum.charAt(cardNum.length() - 1) == bit;
        }


    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


    public static String NumberFormat(float f, int m) {
        return String.format("%." + m + "f", f);
    }

    public static float NumberFormatFloat(float f, int m) {
        String strfloat = NumberFormat(f, m);
        return Float.parseFloat(strfloat);
    }

    //ba
    public static String formatDateTime(long mss) {
        String DateTimes = null;
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        if (days > 0) {
            DateTimes = days + "天";
//                    + hours + "小时" + minutes + "分钟" + seconds + "秒";
        } else if (hours > 0) {
            DateTimes = hours + "小时";
//                    + minutes + "分钟" + seconds + "秒";
        } else if (minutes > 0) {
            DateTimes = minutes + "分钟";
//                    + seconds + "秒";
        } else {
            DateTimes = seconds + "秒";
        }

        return DateTimes;
    }

    public static String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

    public static String timeParse2(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute != 0){
            if (minute < 10) {
                time += "0";
            }
            time += minute + "分钟";
        }
        if (second < 10) {
            time += "0";
        }
        time += second + "秒";
        return time;
    }

    //将年月日时分秒转成毫秒数
    public static long getTimeMillis(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeMillis = 0;
        try {
            Date date = sdf.parse(time);
            timeMillis = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeMillis;
    }

    //将字符串转成保留两位数的字符串
    public static String getDouble(String num) {
        double doubleNum = Double.parseDouble(num);

        return "" + Math.floor(doubleNum * 100 + 0.45) / 100;
    }

}
