package com.rumah.kredit.loan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.rumah.kredit.loan.BuildConfig;
import com.rumah.kredit.loan.R;
import com.rumah.kredit.loan.manager.Constant;
import com.rumah.kredit.loan.manager.MyApp;
import com.rumah.kredit.loan.manager.UpTotal;
import com.rumah.kredit.loan.util.DeviceUtil;
import com.rumah.kredit.loan.util.LogUtil;
import com.rumah.kredit.loan.util.SharePreUtil;

import java.util.Map;

public class LaunchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        UpTotal.pushNewDevice(LaunchActivity.this);

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
