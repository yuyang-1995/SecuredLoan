package com.sec.yn.loan.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sec.yn.loan.R;
import com.sec.yn.loan.global.GlobalData;
import com.sec.yn.loan.product.WebViewActivity;
import com.sec.yn.loan.util.AppUtils;
import com.sec.yn.loan.util.SharePreUtil;

public class MyActivity extends AppCompatActivity {

    private ImageView mIvBack;
    private TextView mTvMobilePhone;
    private TextView mTvVersion;
    private TextView mTvEmail;
    private TextView mTvProtocol;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        initActivity();
    }



    private void initActivity(){

        mIvBack = findViewById(R.id.iv_back);
        mTvMobilePhone = findViewById(R.id.tv_mobile_phone);
        mTvVersion = findViewById(R.id.tv_version);
        mTvEmail = findViewById(R.id.tv_email);
        mTvProtocol = findViewById(R.id.tv_protocol);

        mTvMobilePhone.setText(SharePreUtil.getString(MyActivity.this,
                GlobalData.MOBILE, ""));
        mTvVersion.setText(getString(R.string.version, AppUtils.getVersionName(MyActivity.this)));
        mTvEmail.setVisibility(TextUtils.isEmpty(SharePreUtil.getString(MyActivity.this,
                GlobalData.EMAIL, "")) ? View.GONE : View.VISIBLE);
        mTvEmail.setText(SharePreUtil.getString(MyActivity.this,
                GlobalData.EMAIL, ""));
        mTvProtocol.setVisibility(TextUtils.isEmpty(SharePreUtil.getString(MyActivity.this,
                GlobalData.PROTOCOL, "")) ? View.GONE : View.VISIBLE);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTvProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyActivity.this, WebViewActivity.class)
                        .putExtra("loadUrl", SharePreUtil.getString(MyActivity.this,
                                GlobalData.PROTOCOL, "")));
            }
        });
    }


}
