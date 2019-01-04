package com.rural.loans.rupiah.global;

import com.rural.loans.rupiah.util.DeviceUtil;
import com.rural.loans.rupiah.util.SharePreUtil;
import com.rural.loans.rupiah.util.StringUtil;

public class GlobalData {

    public static final String APP_NUMBER = "Ff";

    public static final String MOBILE = "mobile_phone";
    public static final String PROTOCOL= "protocol";
    public static final String EMAIL= "email";
    public static final String IS_SHOW_POLICY = "is_show_policy";

    /**
     * 获取IV
     * @return
     */
    public static String getIV(){
        return StringUtil.getRandoms(8);
    }

    public static GrobalParams getParams(String iv){
        GrobalParams paramsManage = new GrobalParams(iv);
        return paramsManage
                .setParams("app_no", APP_NUMBER)
                .setParams("channel", "Aa")
                .setParams("mobile", SharePreUtil.getString(GlobalApp.mContext, MOBILE,
                        DeviceUtil.getUniqueId(GlobalApp.mContext)))
                .setParams("lang", "id-id")
                .setParams("time", String.valueOf(System.currentTimeMillis() / 1000));
    }


}
