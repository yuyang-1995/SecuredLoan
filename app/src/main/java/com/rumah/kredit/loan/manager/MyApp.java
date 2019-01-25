package com.rumah.kredit.loan.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.rumah.kredit.loan.BuildConfig;
import com.rumah.kredit.loan.main.MainActivity;
import com.rumah.kredit.loan.util.LogUtil;

public class MyApp extends Application {

    public static Context mContext;

    private long beginTime = 0;
    private long endTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        LogUtil.isDebug = BuildConfig.is_log;



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





}
