package com.sec.yn.loan.global;

import com.sec.yn.loan.util.DeviceUtil;
import com.sec.yn.loan.util.SharePreUtil;
import com.sec.yn.loan.util.StringUtil;

public class GlobalData {

    public static final String APP_NUMBER = "Cs";

    public static final String MOBILE = "mobile_phone";
    public static final String PROTOCOL= "protocol";
    public static final String EMAIL= "email";

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
