package com.shengma.lanjing.utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.text.DecimalFormat;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.tencent.imsdk.utils.IMFunc.base64Encode;

public class Utils {

    private static final String KEY = "lanjing_lanjing_";// AES加密要求key必须要128个比特位（这里需要长度为16，否则会报错）
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    /**
     * Android 音乐播放器应用里，读出的音乐时长为 long 类型以毫秒数为单位，例如：将 234736 转化为分钟和秒应为 03:55 （包含四舍五入）
     * @param duration 音乐时长
     * @return
     */
    public static String timeParse(long duration) {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
    }

    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.0").format(num);
    }


    /**
     64      * AES加密
     65      * @param content 待加密的内容
     66      * @param encryptKey 加密密钥
     67      * @return 加密后的byte[]
     68      */
      private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
                 KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128);
                 Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
                 cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

                 return cipher.doFinal(content.getBytes("utf-8"));
      }




    /**
     80      * AES加密为base 64 code
     81      *
     82      * @param content 待加密的内容
     83      * @param encryptKey 加密密钥
     84      * @return 加密后的base 64 code
     85      */
      public static String aesEncrypt(String content) throws Exception {
                 return base64Encode(aesEncryptToBytes(content, KEY));
             }




}
