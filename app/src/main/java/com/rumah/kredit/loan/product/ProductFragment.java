package com.rumah.kredit.loan.product;

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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rumah.kredit.loan.BuildConfig;
import com.rumah.kredit.loan.R;
import com.rumah.kredit.loan.banner.ConvenientBanner;
import com.rumah.kredit.loan.banner.holder.CBViewHolderCreator;
import com.rumah.kredit.loan.banner.listener.OnItemClickListener;
import com.rumah.kredit.loan.manager.BannerHolderView;
import com.rumah.kredit.loan.model.GlideApp;
import com.rumah.kredit.loan.model.IndexModel;
import com.rumah.kredit.loan.model.ResponseModel;
import com.rumah.kredit.loan.manager.Constant;
import com.rumah.kredit.loan.manager.UpTotal;
import com.rumah.kredit.loan.util.DesUtils;
import com.rumah.kredit.loan.util.LogUtil;
import com.rumah.kredit.loan.util.StringUtil;
import com.rumah.kredit.loan.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .baseUrl(BuildConfig.server_api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductApi productApi = retrofit.create(ProductApi.class);
        String iv = Constant.getIV();
        String data = Constant.getParams(iv)
                .setParams("cid", cId)
                .setParams("page", 1)
                .build();
        productApi.pullProductList(iv, data).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if(null != response.body()){
                    if(response.body().getCode() == 0){
                        String data = response.body().getData();
                        try{
                            data = DesUtils.decode(data, response.body().getIv());
                            IndexModel indexBean = new Gson().fromJson(data, IndexModel.class);
                            if(null != indexBean.getProduct() && indexBean.getProduct().size() > 0){
                                ProductAdapter productAdapter = new ProductAdapter(getActivity(),
                                        indexBean.getProduct(), indexBean.getAds(), cId, position);
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
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                ToastUtil.showCenter(getActivity(), R.string.server_unconnect);
            }
        });


    }



    static class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        static final int TYPE_HEADER = 0;
        static final int TYPE_NORMAL = 1;

        private List<IndexModel.ProductBean> mProductBeanList;
        private List<IndexModel.AdsBean> mAdsBeans;
        private Context mContext;
        private String cateId;
        private int catePosition;

        public ProductAdapter(Context context, List<IndexModel.ProductBean> productBeans,
                              List<IndexModel.AdsBean> adsBeans, String cid, int cposition){
            this.mContext = context;
            this.mProductBeanList = productBeans;
            this.mAdsBeans = adsBeans;
            this.cateId = cid;
            this.catePosition = cposition;
        }

        @Override
        public int getItemViewType(int position) {
            if(catePosition == 0 && position == 0){
                return TYPE_HEADER;
            }
            return TYPE_NORMAL;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if(i == TYPE_HEADER){
                return new HeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.header_product_list, viewGroup, false));
            }
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_product, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if(viewHolder instanceof ViewHolder){
                if(catePosition == 0){
                    i = i - 1;
                }
                final int productPos = i;
                final IndexModel.ProductBean productBean = mProductBeanList.get(i);
                GlideApp.with(mContext)
                        .load(productBean.getLogo())
                        .into(((ViewHolder) viewHolder).mIvLogo);
                ((ViewHolder) viewHolder).mTvTitle.setText(StringUtil.getText(productBean.getName()));
                ((ViewHolder) viewHolder).mtvMsg.setText(Html.fromHtml(mContext.getString(R.string.msg, StringUtil.getText(StringUtil.formatString(Integer.parseInt(productBean.getLoan_amount_max()))),
                        StringUtil.getText(productBean.getRate_interest()) + "%", StringUtil.getText(productBean.getPass_num()))));
                ((ViewHolder) viewHolder).mtvDesc.setText(StringUtil.getText(productBean.getDeclare()));
                ((ViewHolder) viewHolder).mLlItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpTotal.upProductPosition(mContext,
                                cateId, productBean.getId(), catePosition, productPos);
                        Intent intent = new Intent(mContext, ProductActivity.class);
                        intent.putExtra("productId", productBean.getId());
                        mContext.startActivity(intent);
                    }
                });
            }else if(viewHolder instanceof HeaderHolder){
                if(mAdsBeans == null || mAdsBeans.size() <= 0){
                    return;
                }

                final List<String> imageList = new ArrayList<>();
                for(IndexModel.AdsBean bean : mAdsBeans){
                    imageList.add(bean.getImg());
                }

                ((HeaderHolder) viewHolder).mBanner.setCanLoop(imageList.size() > 1 ? true : false);
                ((HeaderHolder) viewHolder).mBanner.startTurning(5000);

                ((HeaderHolder) viewHolder).mBanner.setPages(new CBViewHolderCreator() {
                    @Override
                    public BannerHolderView createHolder() {
                        return new BannerHolderView();
                    }
                }, imageList)
                        .setPageIndicator(new int[]{R.drawable.circle_e5, R.drawable.circle_fd9e02})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                /**
                                 * media_type 1,url，2,跳转产品0不可点击
                                 */
                                if(!TextUtils.isEmpty(mAdsBeans.get(position).getMedia_type())){
                                    if(mAdsBeans.get(position).getMedia_type().equals("1")){
                                        if(!TextUtils.isEmpty(mAdsBeans.get(position).getLink())){
                                            UpTotal.upBannerCxlick(mContext, mAdsBeans.get(position).getId());
                                            Intent intent = new Intent(mContext, WebViewActivity.class);
                                            intent.putExtra("loadUrl", mAdsBeans.get(position).getLink());
                                            mContext.startActivity(intent);
                                        }
                                    }else if(mAdsBeans.get(position).getMedia_type().equals("2")){
                                        UpTotal.upBannerCxlick(mContext, mAdsBeans.get(position).getId());
                                        Intent intent = new Intent(mContext, ProductActivity.class);
                                        intent.putExtra("productId", mAdsBeans.get(position).getLink());
                                        mContext.startActivity(intent);
                                    }
                                }
                            }
                        });
            }
        }

        @Override
        public int getItemCount() {
            return null != mProductBeanList && mProductBeanList.size() > 0 ?
                    catePosition == 0 ? mProductBeanList.size() + 1 : mProductBeanList.size() :
                    catePosition == 0 ? 1 : 0;
        }

        static class ViewHolder extends RecyclerView.ViewHolder{

            private LinearLayout mLlItem;
            private ImageView mIvLogo;
            private TextView mTvTitle;
            private TextView mtvMsg;
            private TextView mtvDesc;

            public ViewHolder(View view){
                super(view);
                mLlItem = view.findViewById(R.id.ll_item);
                mIvLogo = view.findViewById(R.id.iv_logo);
                mTvTitle = view.findViewById(R.id.tv_title);
                mtvMsg = view.findViewById(R.id.tv_msg);
                mtvDesc = view.findViewById(R.id.tv_desc);
            }

        }

        static class HeaderHolder extends RecyclerView.ViewHolder{
            private ConvenientBanner mBanner;

            public HeaderHolder(View view){
                super(view);
                mBanner = view.findViewById(R.id.banner);
            }
        }

    }



}
