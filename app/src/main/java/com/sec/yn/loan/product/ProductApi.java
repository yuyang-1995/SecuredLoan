package com.sec.yn.loan.product;

import com.sec.yn.loan.bean.ResponseBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProductApi {


    @FormUrlEncoded
    @POST("index.index")
    Call<ResponseBean> pullProductList(@Field("iv") String iv, @Field("data") String data);

    @FormUrlEncoded
    @POST("Product.detail")
    Call<ResponseBean> pullProductDetail(@Field("iv") String iv, @Field("data") String data);

    @FormUrlEncoded
    @POST("Product.apply")
    Call<ResponseBean> applyProduct(@Field("iv") String iv, @Field("data") String data);


}
