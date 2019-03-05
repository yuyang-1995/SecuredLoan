package com.damai.kekayaan.anda.manager;

import android.content.Context;


import com.damai.kekayaan.anda.BuildConfig;
import com.damai.kekayaan.anda.model.ResponseModel;
import com.damai.kekayaan.anda.util.DeviceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpTotal {


    /**
     * 上传激活
     */
    public static void pushNewDevice(Context mContext){
        String iv = Constant.getIV();
        String data = Constant
                .getParams(iv)
                .setParams("udid", DeviceUtil.getUniqueId(mContext))
                .setParams("type", "1")
                .build();
        upData(iv, data);
    }

    /**
     * 上传点击数据
     * 1.banner的点击数据
     *2.
     */
    public static void upBannerCxlick(Context mContext, String id){
        String iv = Constant.getIV();
        String data = Constant
                .getParams(iv)
                .setParams("udid", DeviceUtil.getUniqueId(mContext))
                .setParams("type", "6")
                .setParams("id", id)
                .build();
        upData(iv, data);
    }

    /**
     * 上传点击位置数据
     * 3.首页列表每个位置的点击数据
     */
    public static void upProductPosition(Context mContext, String cid, String id,
                                         int cPosition, int position){
        String iv = Constant.getIV();
        String data = Constant
                .getParams(iv)
                .setParams("udid", DeviceUtil.getUniqueId(mContext))
                .setParams("type", "4")
                .setParams("cId", cid)
                .setParams("id", id)
                .setParams("cPosition", cPosition)
                .setParams("postion", position)
                .build();
        upData(iv, data);
    }

    /**
     * 上传停留时间
     * @param second
     */
    public static void upResidenceTime(Context mContext, long second){
        String iv = Constant.getIV();
        String data = Constant
                .getParams(iv)
                .setParams("udid", DeviceUtil.getUniqueId(mContext))
                .setParams("type", "2")
                .setParams("app_eff_time", String.valueOf(second))
                .build();
        upData(iv, data);
    }



    /**
     * 产品详情访问统计
     * @param mContext
     * @param id
     */
    public static void upProducAccess(Context mContext, String id){
        String iv = Constant.getIV();
        String data = Constant
                .getParams(iv)
                .setParams("udid", DeviceUtil.getUniqueId(mContext))
                .setParams("type", "7")
                .setParams("id", id)
                .build();
        upData(iv, data);
    }


    private static void upData(String iv, String data){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.server_api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UpTotalApi globalUpTotalApi = retrofit.create(UpTotalApi.class);

        globalUpTotalApi.upTotal(iv, data).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

    }
    
}
