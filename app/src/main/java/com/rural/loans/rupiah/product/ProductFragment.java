package com.rural.loans.rupiah.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rural.loans.rupiah.R;
import com.rural.loans.rupiah.bean.IndexBean;
import com.rural.loans.rupiah.bean.ResponseBean;
import com.rural.loans.rupiah.global.GlobalApi;
import com.rural.loans.rupiah.global.GlobalData;
import com.rural.loans.rupiah.global.GlobalUpTotal;
import com.rural.loans.rupiah.util.DesUtils;
import com.rural.loans.rupiah.util.StringUtil;
import com.rural.loans.rupiah.util.ToastUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductFragment extends Fragment {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private int position;
    private String cId;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(null != getArguments()){
            position = getArguments().getInt("position", 0);
            cId = getArguments().getString("cId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, null);
        initView(view);
        return view;
    }


    private void initView(View view){

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.RED);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullProduct();
            }
        });

        pullProduct();

    }


    private void pullProduct(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductApi productApi = retrofit.create(ProductApi.class);
        String iv = GlobalData.getIV();
        String data = GlobalData.getParams(iv)
                .setParams("cid", cId)
                .setParams("page", 1)
                .build();
        productApi.pullProductList(iv, data).enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if(null != response.body()){
                    if(response.body().getCode() == 0){
                        String data = response.body().getData();
                        try{
                            data = DesUtils.decode(data, response.body().getIv());
                            IndexBean indexBean = new Gson().fromJson(data, IndexBean.class);
                            if(null != indexBean.getProduct() && indexBean.getProduct().size() > 0){
                                ProductAdapter productAdapter = new ProductAdapter(getActivity(),
                                        indexBean.getProduct(), cId, position);
                                mRecyclerView.setAdapter(productAdapter);

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        ToastUtil.showCenter(getActivity(), response.body().getMsg());
                    }
                }else{
                    ToastUtil.showCenter(getActivity(), R.string.server_unconnect);
                }
            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                ToastUtil.showCenter(getActivity(), R.string.server_unconnect);
            }
        });


    }



    static class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

        private List<IndexBean.ProductBean> mProductBeanList;
        private Context mContext;
        private String cateId;
        private int catePosition;

        public ProductAdapter(Context context, List<IndexBean.ProductBean> productBeans, String cid,
                              int cposition){
            this.mContext = context;
            this.mProductBeanList = productBeans;
            this.cateId = cid;
            this.catePosition = cposition;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_product, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            final IndexBean.ProductBean productBean = mProductBeanList.get(i);
            Glide.with(mContext)
                    .load(productBean.getLogo())
                    .into(viewHolder.mIvLogo);
            viewHolder.mTvTitle.setText(StringUtil.getText(productBean.getName()));
            viewHolder.mtvMsg.setText(Html.fromHtml(mContext.getString(R.string.msg, StringUtil.getText(StringUtil.formatString(Integer.parseInt(productBean.getLoan_amount_max()))),
                    StringUtil.getText(productBean.getRate_interest()) + "%", StringUtil.getText(productBean.getPass_num()))));
            viewHolder.mLlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalUpTotal.upProductPosition(mContext,
                            cateId, productBean.getId(), catePosition, i);
                    Intent intent = new Intent(mContext, ProductActivity.class);
                    intent.putExtra("productId", productBean.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return null != mProductBeanList && mProductBeanList.size() > 0 ? mProductBeanList.size() : 0;
        }

        static class ViewHolder extends RecyclerView.ViewHolder{

            private LinearLayout mLlItem;
            private ImageView mIvLogo;
            private TextView mTvTitle;
            private TextView mtvMsg;

            public ViewHolder(View view){
                super(view);
                mLlItem = view.findViewById(R.id.ll_item);
                mIvLogo = view.findViewById(R.id.iv_logo);
                mTvTitle = view.findViewById(R.id.tv_title);
                mtvMsg = view.findViewById(R.id.tv_msg);
            }

        }

    }



}
