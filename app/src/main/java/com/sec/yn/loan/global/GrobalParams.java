package com.sec.yn.loan.global;

import android.text.TextUtils;

import com.sec.yn.loan.util.DesUtils;
import com.sec.yn.loan.util.LogUtil;

public class GrobalParams {


    private StringBuilder sb = new StringBuilder("{");

    private String iv;

    public GrobalParams(String iv){
        this.iv = iv;
    }

    public GrobalParams(){

    }

    /**
     * 拼接参数
     * @param key
     * @param value
     * @return
     */
    public GrobalParams setParams(String key, String value){
        String endodeKey = key;
        String encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":\"").append(encodeValue).append("\",");
        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public GrobalParams setParams(String key, int value){
        String endodeKey = key;
        int encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":\"").append(encodeValue).append("\",");
        return this;
    }

    /**
     * 拼接参数
     * @param key
     * @param value
     * @return
     */
    public GrobalParams setListParam(String key, String value){
        String endodeKey = key;
        String encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":").append(encodeValue).append(",");
        return this;
    }

    /**
     * 拼接参数
     * @param key
     * @param value
     * @return
     */
    public GrobalParams setListParam(String key, int value){
        String endodeKey = key;
        int encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":").append(encodeValue).append(",");
        return this;
    }

    /**
     * 返回最终的拼接 字段
     * @return
     */
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
