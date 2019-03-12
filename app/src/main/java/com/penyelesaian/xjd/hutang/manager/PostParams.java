package com.penyelesaian.xjd.hutang.manager;

import android.text.TextUtils;

import com.penyelesaian.xjd.hutang.util.DesUtils;
import com.penyelesaian.xjd.hutang.util.LogUtil;

public class PostParams {


    private StringBuilder sb = new StringBuilder("{");

    private String iv;

    public PostParams(String iv){
        this.iv = iv;
    }

    public PostParams(){

    }

    public PostParams setParams(String key, String value){
        String endodeKey = key;
        String encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":\"").append(encodeValue).append("\",");
        return this;
    }

    public PostParams setParams(String key, int value){
        String endodeKey = key;
        int encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":\"").append(encodeValue).append("\",");
        return this;
    }

    public PostParams setListParam(String key, String value){
        String endodeKey = key;
        String encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":").append(encodeValue).append(",");
        return this;
    }

    public PostParams setListParam(String key, int value){
        String endodeKey = key;
        int encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":").append(encodeValue).append(",");
        return this;
    }

    public String build(){
        String str = sb.toString();
        if(str.endsWith(",")){
            str = str.substring(0,str.length()-1)+"}";
        }else{
            str = str+"}";
        }
        LogUtil.e("data-->"+str);
        if(!TextUtils.isEmpty(iv)){
            try{
                str = DesUtils.encode(str, iv);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return str;
    }

}
