package com.pecah.kan.uangnya.api;

import com.pecah.kan.uangnya.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("User.login")
    Call<ResponseModel> login(@Field("iv") String iv, @Field("data") String data);


}
