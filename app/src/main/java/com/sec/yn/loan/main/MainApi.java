package com.sec.yn.loan.main;

import com.sec.yn.loan.bean.ResponseBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MainApi {


    @FormUrlEncoded
    @POST("app.init")
    Call<ResponseBean> pullInitData(@Field("iv") String iv, @Field("data") String data);

}
