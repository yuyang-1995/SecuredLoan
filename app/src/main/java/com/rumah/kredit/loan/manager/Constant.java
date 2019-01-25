package com.rumah.kredit.loan.manager;

import com.rumah.kredit.loan.BuildConfig;
import com.rumah.kredit.loan.util.DeviceUtil;
import com.rumah.kredit.loan.util.SharePreUtil;
import com.rumah.kredit.loan.util.StringUtil;

public class Constant {

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

    public static PostParams getParams(String iv){
        PostParams paramsManage = new PostParams(iv);
        return paramsManage
                .setParams("app_no", BuildConfig.app_no)
                .setParams("channel", "Aa")
                .setParams("mobile", SharePreUtil.getString(MyApp.mContext, MOBILE,
                        DeviceUtil.getUniqueId(MyApp.mContext)))
                .setParams("lang", "id-id")
                .setParams("time", String.valueOf(System.currentTimeMillis() / 1000));
    }


}
