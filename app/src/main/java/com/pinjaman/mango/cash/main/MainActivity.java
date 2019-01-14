package com.pinjaman.mango.cash.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.pinjaman.mango.cash.BuildConfig;
import com.pinjaman.mango.cash.R;
import com.pinjaman.mango.cash.model.InitModel;
import com.pinjaman.mango.cash.model.ResponseModel;
import com.pinjaman.mango.cash.manager.Constant;
import com.pinjaman.mango.cash.product.ProductFragment;
import com.pinjaman.mango.cash.dialog.LoadingDialog;
import com.pinjaman.mango.cash.dialog.ProtocolDialog;
import com.pinjaman.mango.cash.util.DesUtils;
import com.pinjaman.mango.cash.util.LogUtil;
import com.pinjaman.mango.cash.util.SharePreUtil;
import com.pinjaman.mango.cash.util.ToastUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvMenu;
    private TabLayout mTabLyout;
    private ViewPager mViewPager;

    private InitModel mInitBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivity();

    }


    private void initActivity(){

        mIvMenu = findViewById(R.id.iv_menu);
        mTabLyout = findViewById(R.id.tabLyout);
        mViewPager = findViewById(R.id.viewPager);

        mIvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyActivity.class));
            }
        });

        pullData();
    }


    private void pullData(){

        LoadingDialog.show(MainActivity.this, R.string.data_loading);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.server_api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MainApi mainApi = retrofit.create(MainApi.class);
        String iv = Constant.getIV();
        String data = Constant.getParams(iv)
                .setParams("company_name", getString(R.string.app_name))
                .setParams("product_name", getString(R.string.app_name))
                .build();
        mainApi.pullInitData(iv, data).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                LoadingDialog.dismiss(MainActivity.this);
                if(null != response.body()){
                    if(response.body().getCode() == 0){
                        LogUtil.e("result-->" + response.body().toString());
                        String result = response.body().getData();
                        LogUtil.e("result--->" + result);
                        try{
                            result = DesUtils.decode(result, response.body().getIv());
                            LogUtil.e("result--->" + result);
                            mInitBean = new Gson().fromJson(result, InitModel.class);
                            setUI();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        ToastUtil.showCenter(MainActivity.this, response.body().getMsg());
                    }
                }else{
                    ToastUtil.showCenter(MainActivity.this, R.string.server_unconnect);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                LoadingDialog.dismiss(MainActivity.this);
                ToastUtil.showCenter(MainActivity.this, R.string.server_unconnect);
            }
        });
    }


    private void setUI(){

        LogUtil.e("data--->" + mInitBean);
        if(null != mInitBean){
            if(null != mInitBean.getCate_list() && mInitBean.getCate_list().size() > 0){

                CateAdapter cateAdapter = new CateAdapter(getSupportFragmentManager(), mInitBean.getCate_list());
                mViewPager.setAdapter(cateAdapter);
                mViewPager.setOffscreenPageLimit(mInitBean.getCate_list().size());
                mTabLyout.setupWithViewPager(mViewPager);

            }

            SharePreUtil.putString(MainActivity.this, Constant.EMAIL, mInitBean.getEmail());
            SharePreUtil.putString(MainActivity.this, Constant.PROTOCOL, mInitBean.getPrivacy_policy());

            if (!TextUtils.isEmpty(mInitBean.getPrivacy_policy()) &&
                    !SharePreUtil.getBoolean(MainActivity.this, Constant.IS_SHOW_POLICY, false)) {
                ProtocolDialog prolicyDialog = new ProtocolDialog();
                Bundle bundle = new Bundle();
                bundle.putString("url", mInitBean.getPrivacy_policy());
                prolicyDialog.setArguments(bundle);
                prolicyDialog.show(getSupportFragmentManager(), "ProtocolDialog");
            }

        }
    }

    static class CateAdapter extends FragmentPagerAdapter {

        private List<InitModel.CateListBean> mCateListBeans;

        public CateAdapter(FragmentManager fm, List<InitModel.CateListBean> cateListBeans) {
            super(fm);
            this.mCateListBeans = cateListBeans;
        }

        @Override
        public Fragment getItem(int position) {
            ProductFragment fragment = new ProductFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putString("cId", mCateListBeans.get(position).getId());
            bundle.putString("title", mCateListBeans.get(position).getName());
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mCateListBeans.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCateListBeans.get(position).getName();
        }
    }

    private long currentTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - currentTime < 1000) {
            finish();
        } else {
            ToastUtil.showCenter(this, R.string.reclicl_exit);
            currentTime = System.currentTimeMillis();
        }
    }
}
