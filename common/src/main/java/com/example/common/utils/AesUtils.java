package com.example.common.utils;

import com.example.common.tool.RxEncryptTool;

import java.math.BigInteger;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AesUtils {
    //密钥 (需要前端和后端保持一致)
    private static final String KEY = "WeWin2019HWK666P";
    //算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    
    /** 
     * aes解密 
     * @param encrypt   内容 
     * @return 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encrypt) {  
        try {
            return aesDecrypt(encrypt, KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }  
    }  
      
    /** 
     * aes加密 
     * @param content 
     * @return 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content) {  
        try {
            return aesEncrypt(content, KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }  
    }  
  
    /** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符串 
     */  
    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
//        return RxEncodeTool.base64Encode2String(bytes);
        return Base64Util.encode(bytes);//.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return decryptBASE64(base64Code);
//        return TextUtils.isEmpty(base64Code) ? null : Base64Util.decode(base64Code);//BASE64Decoder().decodeBuffer(base64Code);
//        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
//        return decryptBASE64(base64Code);
//        return TextUtils.isEmpty(base64Code) ? null : decryptBASE64(base64Code);//BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * BASE64Encoder 加密
     *
     * @param data
     *            要加密的数据
     * @return 加密后的字符串
     */
    public static String encryptBASE64(byte[] data) {
        // BASE64Encoder encoder = new BASE64Encoder();
        // String encode = encoder.encode(data);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Encoder
        Base64.Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString(data);
        return encode;
    }
    /**
     * BASE64Decoder 解密
     *
     * @param data
     *            要解密的字符串
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] decryptBASE64(String data) throws Exception {
        // BASE64Decoder decoder = new BASE64Decoder();
        // byte[] buffer = decoder.decodeBuffer(data);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] buffer = decoder.decode(data);
        return buffer;
    }


    /**
     * AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
//        IvParameterSpec ips = new IvParameterSpec( iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
  
        return cipher.doFinal(content.getBytes("utf-8"));  
    }

    // 向量 可有可无 终端后台也要约定
    private final static String iv = "123456790123456" ;
  
  
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
  
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        return new String(decryptBytes);  
    }  
  
  
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
//        return TextUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }
    
    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {  
        String content = "d123456";
        System.out.println("加密前：" + content);  
        System.out.println("加密密钥和解密密钥：" + KEY);  
        String encrypt = aesEncrypt(content, KEY);
        String encrypt = RxEncryptTool.encrypt3DES2HexString(content.getBytes(), KEY.getBytes());
//        String encrypt = AndroidDes3Util.encode(content);
        System.out.println("加密后：" + encrypt);
//        String decrypt = aesDecrypt(encrypt, KEY);
//        String decrypt = Base64Util.encode(RxEncryptTool.decryptHexStringAES(encrypt,KEY.getBytes()));
//        System.out.println("解密后：" + decrypt);
    } 
}