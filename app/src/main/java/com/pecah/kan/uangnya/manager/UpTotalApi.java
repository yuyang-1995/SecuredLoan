package com.pecah.kan.uangnya.manager;

import com.pecah.kan.uangnya.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UpTotalApi {


    @FormUrlEncoded
    @POST("App.data_report")
    Call<ResponseModel> upTotal(@Field("iv") String iv, @Field("data") String data);

    @FormUrlEncoded
    @POST("App.user_data_report")
    Call<ResponseModel> upData(@Field("iv") String iv, @Field("data") String data);

}
