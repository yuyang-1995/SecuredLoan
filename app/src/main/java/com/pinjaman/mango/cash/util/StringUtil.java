package com.pinjaman.mango.cash.util;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Random;

public class StringUtil {


    /**
     * 获取文本
     * @param str
     * @return
     */
    public static String getText(String str){
        return TextUtils.isEmpty(str) ? "" : str;
    }

    /**
     * 生成随机字符串
     * @param length
     * @return
     */
    public static String getRandoms(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 每三位数字加一个逗号
     * @param data
     * @return
     */
    public static String formatString(int data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(data);
    }

}
