package com.pecah.kan.uangnya.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerLib;
import com.pecah.kan.uangnya.R;
import com.pecah.kan.uangnya.manager.AppsFlyerEvent;
import com.pecah.kan.uangnya.manager.Constant;
import com.pecah.kan.uangnya.manager.UpTotal;
import com.pecah.kan.uangnya.util.SharePreUtil;

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
