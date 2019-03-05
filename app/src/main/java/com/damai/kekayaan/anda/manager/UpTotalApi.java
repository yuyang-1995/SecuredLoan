package com.damai.kekayaan.anda.manager;

import com.damai.kekayaan.anda.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UpTotalApi {


    @FormUrlEncoded
    @POST("App.data_report")
    Call<ResponseModel> upTotal(@Field("iv") String iv, @Field("data") String data);


}
