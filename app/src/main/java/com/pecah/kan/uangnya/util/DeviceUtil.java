package com.pecah.kan.uangnya.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;


public class DeviceUtil {

    /**
     * 获取设备IMEI码
     * @param context 上下文
     * @return IMEI码，获取失败则返回null
     */
    public  static String getDeviceId(Context context){
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
    /**
     * 获取设备IMSI码
     * @param context 上下文
     * @return IMSI码，获取失败则返回null
     */
    private static String getIMSI(Context context){
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }
    /**
     * 获取手机号码
     * @param context 上下文
     * @return 手机号码，获取失败则返回null
     */
    private static String getSimNumber(Context context){
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }
    /**
     * 获取sim卡序列号
     * @param context 上下文
     * @return sim卡序列号，获取失败返回null
     */
    private static String getSimSerialNumber(Context context){
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
    }
    /**
     * android id
     * @param context 上下文
     * @return android id ，获取失败则返回null
     */
    @SuppressWarnings("deprecation")
    public static String getAndroidId(Context context){
        return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
    }
    /**
     * 获取设备唯一标识码，优先顺序是：IMEI，IMSI，手机序列号，手机码号，android id
     * @param context 上下文
     * @return 返回手机唯一标识，一定会返回一个唯一标识码，可能是上面5种码
     */
    public static String getUniqueId(Context context){
        String id = null;
        try {
            id = getDeviceId(context);//IMEI
        } catch (Exception e) {
            // TODO Auto-generated catch block

        } catch (Error e) {
            // TODO: handle exception

        }
        if(id == null || id.equals("")){
            try {
                id = getIMSI(context);//IMSI
            } catch (Exception e) {
                // TODO: handle exception
            } catch (Error e) {
                // TODO: handle exception

            }
            if(id == null || id.equals("")){
                try {
                    id = getSimSerialNumber(context);//序列号
                } catch (Exception e) {
                    // TODO: handle exception

                } catch (Error e) {
                    // TODO: handle exception

                }
                if(id == null || id.equals("")){
                    try {
                        id = getSimNumber(context);//获取手机号码
                    } catch (Exception e) {
                        // TODO: handle exception

                    } catch (Error e) {
                        // TODO: handle exception

                    }
                    if(id != null && !id.equals("")){
                        return id;
                    }
                }else{
                    return id;
                }
            }else{
                return id;
            }
        }else{
            return id;
        }
        return getAndroidId(context);//获取android id
    }

}
