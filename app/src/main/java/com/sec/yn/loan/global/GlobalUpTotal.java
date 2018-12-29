package com.sec.yn.loan.global;

import android.content.Context;


import com.sec.yn.loan.bean.ResponseBean;
import com.sec.yn.loan.util.DeviceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalUpTotal {


    /**
     * 上传激活
     */
    public static void pushNewDevice(Context mContext){
        String iv = GlobalData.getIV();
        String data = GlobalData
                .getParams(iv)
                .setParams("udid", DeviceUtil.getUniqueId(mContext))
                .setParams("type", "1")
                .build();
        upData(iv, data);
    }

    /**
     * 上传点击位置数据
     * 3.首页列表每个位置的点击数据
     */
    public static void upProductPosition(Context mContext, String cid, String id,
                                         int cPosition, int position){
        String iv = GlobalData.getIV();
        String data = GlobalData
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
        String iv = GlobalData.getIV();
        String data = GlobalData
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
        String iv = GlobalData.getIV();
        String data = GlobalData
                .getParams(iv)
                .setParams("udid", DeviceUtil.getUniqueId(mContext))
                .setParams("type", "7")
                .setParams("id", id)
                .build();
        upData(iv, data);
    }


    private static void upData(String iv, String data){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GlobalUpTotalApi globalUpTotalApi = retrofit.create(GlobalUpTotalApi.class);

        globalUpTotalApi.upTotal(iv, data).enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {

            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {

            }
        });

    }
    
}
