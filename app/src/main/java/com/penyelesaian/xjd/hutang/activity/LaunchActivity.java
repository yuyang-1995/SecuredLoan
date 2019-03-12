package com.penyelesaian.xjd.hutang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.penyelesaian.xjd.hutang.BuildConfig;
import com.penyelesaian.xjd.hutang.R;
import com.penyelesaian.xjd.hutang.manager.AppsFlyerEvent;
import com.penyelesaian.xjd.hutang.manager.Constant;
import com.penyelesaian.xjd.hutang.manager.MyApp;
import com.penyelesaian.xjd.hutang.manager.UpTotal;
import com.penyelesaian.xjd.hutang.util.DeviceUtil;
import com.penyelesaian.xjd.hutang.util.LogUtil;
import com.penyelesaian.xjd.hutang.util.SharePreUtil;

import java.util.Map;

public class LaunchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        UpTotal.pushNewDevice(LaunchActivity.this);

        AppsFlyerLib.getInstance().trackEvent(this, AppsFlyerEvent.APP_OPEN, null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(SharePreUtil.getString(LaunchActivity.this, Constant.MOBILE, ""))){
                    startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                }else{
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);

    }




}
