package com.sec.yn.loan.login;

import com.sec.yn.loan.bean.ResponseBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("User.login")
    Call<ResponseBean> login(@Field("iv") String iv, @Field("data") String data);


}
