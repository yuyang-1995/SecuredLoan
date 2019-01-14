package com.pinjaman.mango.cash.product;

import com.pinjaman.mango.cash.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProductApi {


    @FormUrlEncoded
    @POST("index.index")
    Call<ResponseModel> pullProductList(@Field("iv") String iv, @Field("data") String data);

    @FormUrlEncoded
    @POST("Product.detail")
    Call<ResponseModel> pullProductDetail(@Field("iv") String iv, @Field("data") String data);

    @FormUrlEncoded
    @POST("Product.apply")
    Call<ResponseModel> applyProduct(@Field("iv") String iv, @Field("data") String data);


}
