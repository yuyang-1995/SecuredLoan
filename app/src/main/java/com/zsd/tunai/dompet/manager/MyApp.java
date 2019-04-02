package com.zsd.tunai.dompet.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.zsd.tunai.dompet.BuildConfig;
import com.zsd.tunai.dompet.activity.MainActivity;
import com.zsd.tunai.dompet.util.DeviceUtil;
import com.zsd.tunai.dompet.util.LogUtil;

import java.util.Map;

public class MyApp extends Application {

    public static Context mContext;

    private long beginTime = 0;
    private long endTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        initAppsFlyer();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                beginTime = System.currentTimeMillis() / 1000;
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                endTime = endTime + System.currentTimeMillis() / 1000 - beginTime;
                if(activity instanceof MainActivity){
                    UpTotal.upResidenceTime(activity, endTime);
                }
            }
        });


    }



    /**
     * 初始化AppsFlyer
     */
    private void initAppsFlyer(){

        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> map) { }

            @Override
            public void onInstallConversionFailure(String s) { }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {

            }

            @Override
            public void onAttributionFailure(String s) {
            }
        };
        AppsFlyerLib.getInstance().init(BuildConfig.af_key, conversionListener, getApplicationContext());
        AppsFlyerLib.getInstance().setImeiData(DeviceUtil.getUniqueId(this));
        AppsFlyerLib.getInstance().setAndroidIdData(DeviceUtil.getAndroidId(this));
        AppsFlyerLib.getInstance().startTracking(this, BuildConfig.af_key);
        //后台运行会话追踪
        AppsFlyerLib.getInstance().reportTrackSession(this);
        LogUtil.e("appsFlyerUID--->" + AppsFlyerLib.getInstance().getAppsFlyerUID(this));
        AppsFlyerLib.getInstance().setDebugLog(false);
    }


}
