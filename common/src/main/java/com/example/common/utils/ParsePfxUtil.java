package com.example.common.utils;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * 获取证书文件（pfx格式）
 * Created by Administrator on 2018/12/7.
 */

public class ParsePfxUtil {

    public static final String PRI_KEY="PRI_KEY";
    public static final String PUB_KEY="PUB_KEY";

    public  static final String NAME = "1577712_api.i-fucai.com.pfx";
    public  static final String PWD="jo4EO2zv";


    /**
     * 解析Pfx
     * @param mContext
     * @param pfxName
     * @param pwd
     * @return
     */
    public static Map parsePfx(Context mContext, String pfxName, String pwd) {

        Map keyMap = new HashMap();
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            InputStream fis = null;
            try {
                fis =mContext.getAssets().open(pfxName);
            } catch (Exception e) {
                Log.i("sss", "new FileInputStream fail");
            }
            char[] nPassword = pwd.toCharArray();
            ks.load(fis, nPassword);
            fis.close();
            Enumeration aliases = ks.aliases();
            String keyAlias = null;
            PrivateKey prikey = null;
            while (true) {
                try {
                    keyAlias = (String) aliases.nextElement();
                    prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
                    if (null != prikey) {
                        break;
                    } else {
                        Log.i("sss", "prikey is null");
                    }
                } catch (NoSuchElementException e) {
                    Log.i("sss", "NoSuchElementException");
                    return null;
                }
            }
            Certificate cert = (Certificate) ks.getCertificate(keyAlias);


            Log.i("sss", "while after");
            if (null !=prikey && prikey.getEncoded().length > 0) {
                keyMap.put(PRI_KEY, prikey.getEncoded());
                Log.i("sss", "private_key is :" + prikey.getEncoded());
            } else {
                Log.i("sss", "private_key is null");
            }
            Log.i("sss", "prikey after");
            if (null !=cert &&cert.getEncoded().length > 0) {
                keyMap.put(PUB_KEY, cert.getEncoded());
                Log.i("sss", "PUBKIC_KEY is :" + cert.getEncoded());
            } else {
                Log.i("sss", "public_key is null");
            }
            return keyMap;
        }
        catch (Exception e){
            Log.i("sss", "parsePfx will return null");
            return null;
        }
    }

}
