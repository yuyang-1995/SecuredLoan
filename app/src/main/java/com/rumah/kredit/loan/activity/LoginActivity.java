package com.rumah.kredit.loan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.rumah.kredit.loan.BuildConfig;
import com.rumah.kredit.loan.R;
import com.rumah.kredit.loan.api.LoginApi;
import com.rumah.kredit.loan.model.ResponseModel;
import com.rumah.kredit.loan.manager.Constant;
import com.rumah.kredit.loan.dialog.LoadingDialog;
import com.rumah.kredit.loan.util.LogUtil;
import com.rumah.kredit.loan.util.NetUtil;
import com.rumah.kredit.loan.util.SharePreUtil;
import com.rumah.kredit.loan.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mTilLoanNum;
    private TextInputEditText mEtLoanNum;
    private TextInputLayout mTilMobilePhone;
    private TextInputEditText mEtMobilePhone;
    private TextInputLayout mTilPassword;
    private TextInputEditText mEtPassword;
    private Button mBtnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initActivity();
    }

    private void initActivity(){

        mTilLoanNum = findViewById(R.id.til_loan_num);
        mEtLoanNum = findViewById(R.id.et_loan_num);
        mTilMobilePhone = findViewById(R.id.til_mobile_phone);
        mEtMobilePhone = findViewById(R.id.et_mobile_phone);
        mTilPassword = findViewById(R.id.til_password);
        mEtPassword = findViewById(R.id.et_password);
        mBtnLogin = findViewById(R.id.btn_login);

        mEtLoanNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    if(Integer.parseInt(s.toString()) < 100000 || Integer.parseInt(s.toString()) > 20000000){
                        mTilLoanNum.setError(getString(R.string.input_real_loan_num));
                        mTilLoanNum.setErrorEnabled(true);
                    }else{
                        mTilLoanNum.setErrorEnabled(false);
                    }
                }else{
                    mTilLoanNum.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkData();
            }
        });

        mEtMobilePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    mTilMobilePhone.setError(getString(R.string.input_real_telephone));
                    mTilMobilePhone.setErrorEnabled(true);
                }else{
                    mTilMobilePhone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkData();
            }
        });

        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 6 || s.length() > 16){
                    mTilPassword.setError(getString(R.string.input_real_password));
                    mTilPassword.setErrorEnabled(true);
                }else{
                    mTilPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkData();
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void checkData(){
        try{
            if(!TextUtils.isEmpty(mEtLoanNum.getText().toString())){
                if((Integer.parseInt(mEtLoanNum.getText().toString()) >= 100000
                        && Integer.parseInt(mEtLoanNum.getText().toString()) <= 20000000) &&
                        !TextUtils.isEmpty(mEtMobilePhone.getText().toString().trim()) &&
                        (mEtPassword.getText().toString().trim().length() >= 6 &&
                                mEtPassword.getText().toString().trim().length() <= 16)){
                    mBtnLogin.setBackgroundResource(R.drawable.shape_button);
                    mBtnLogin.setClickable(true);
                }else{
                    mBtnLogin.setBackgroundResource(R.drawable.shape_button_unclick);
                    mBtnLogin.setClickable(false);
                }
            }else{
                mBtnLogin.setBackgroundResource(R.drawable.shape_button_unclick);
                mBtnLogin.setClickable(false);
            }
        }catch (NumberFormatException e){
            mBtnLogin.setBackgroundResource(R.drawable.shape_button_unclick);
            mBtnLogin.setClickable(false);
        }
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
