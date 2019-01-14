package com.pinjaman.mango.cash.main;

import com.pinjaman.mango.cash.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MainApi {


    @FormUrlEncoded
    @POST("app.init")
    Call<ResponseModel> pullInitData(@Field("iv") String iv, @Field("data") String data);

}
