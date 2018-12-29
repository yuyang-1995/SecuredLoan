package com.sec.yn.loan.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.sec.yn.loan.R;
import com.sec.yn.loan.global.GlobalData;
import com.sec.yn.loan.global.GlobalUpTotal;
import com.sec.yn.loan.main.MainActivity;
import com.sec.yn.loan.util.SharePreUtil;

public class LaunchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        GlobalUpTotal.pushNewDevice(LaunchActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(SharePreUtil.getString(LaunchActivity.this, GlobalData.MOBILE, ""))){
                    startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                }else{
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);

    }




}
