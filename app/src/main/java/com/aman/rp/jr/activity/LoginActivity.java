package com.aman.rp.jr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.appsflyer.AppsFlyerLib;
import com.aman.rp.jr.BuildConfig;
import com.aman.rp.jr.R;
import com.aman.rp.jr.api.LoginApi;
import com.aman.rp.jr.manager.AppsFlyerEvent;
import com.aman.rp.jr.model.ResponseModel;
import com.aman.rp.jr.manager.Constant;
import com.aman.rp.jr.dialog.LoadingDialog;
import com.aman.rp.jr.util.LogUtil;
import com.aman.rp.jr.util.NetUtil;
import com.aman.rp.jr.util.SharePreUtil;
import com.aman.rp.jr.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mEtLoanNum;
    private TextInputEditText mEtMobilePhone;
    private TextInputEditText mEtPassword;
    private Button mBtnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initActivity();
    }

    private void initActivity(){

        mEtLoanNum = findViewById(R.id.et_loan_num);
        mEtMobilePhone = findViewById(R.id.et_mobile_phone);
        mEtPassword = findViewById(R.id.et_password);
        mBtnLogin = findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mEtLoanNum.getText().toString())){
                    if(Integer.parseInt(mEtLoanNum.getText().toString()) < 100000 ||
                            Integer.parseInt(mEtLoanNum.getText().toString()) > 20000000){
                        ToastUtil.showCenter(LoginActivity.this, R.string.input_real_loan_num);
                    }else{
                        if(TextUtils.isEmpty(mEtMobilePhone.getText().toString())){
                            ToastUtil.showCenter(LoginActivity.this, R.string.input_real_telephone);
                        }else{
                            if(mEtPassword.getText().length() < 6 || mEtPassword.getText().length() > 16){
                                ToastUtil.showCenter(LoginActivity.this, R.string.input_real_password);
                            }else{
                                login();
                            }
                        }
                    }
                }else{
                    ToastUtil.showCenter(LoginActivity.this, R.string.input_real_loan_num);
                }

            }
        });
    }

    private void login(){

        if(!NetUtil.isConnected(LoginActivity.this)){
            ToastUtil.showCenter(LoginActivity.this, R.string.no_network);
            return;
        }

        LoadingDialog.show(LoginActivity.this, R.string.login);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.server_api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginApi loginApi = retrofit.create(LoginApi.class);

        String iv = Constant.getIV();
        String data = Constant.getParams(iv)
                .setParams("iv", iv)
                .setParams("mobile", mEtMobilePhone.getText().toString().trim())
                .setParams("loan_amount", mEtLoanNum.getText().toString().trim())
                .setParams("password", mEtPassword.getText().toString().trim())
                .build();

        loginApi.login(iv, data).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                LoadingDialog.dismiss(LoginActivity.this);
                if(null != response.body()){
                    if(response.body().getCode() == 0){
                        LogUtil.e("result-->" + response.body().toString());
                        AppsFlyerLib.getInstance().trackEvent(LoginActivity.this,
                                AppsFlyerEvent.LOGIN, null);
                        SharePreUtil.putString(LoginActivity.this, Constant.MOBILE,
                                mEtMobilePhone.getText().toString().trim());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        ToastUtil.showCenter(LoginActivity.this, response.body().getMsg());
                    }
                }else{
                    ToastUtil.showCenter(LoginActivity.this, R.string.server_unconnect);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                LoadingDialog.dismiss(LoginActivity.this);
                ToastUtil.showCenter(LoginActivity.this, R.string.server_unconnect);
            }
        });

    }


}
