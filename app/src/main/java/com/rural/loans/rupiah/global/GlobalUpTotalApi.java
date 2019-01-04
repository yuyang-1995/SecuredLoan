package com.rural.loans.rupiah.global;

import com.rural.loans.rupiah.bean.ResponseBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GlobalUpTotalApi {


    @FormUrlEncoded
    @POST("App.data_report")
    Call<ResponseBean> upTotal(@Field("iv") String iv, @Field("data") String data);


}
