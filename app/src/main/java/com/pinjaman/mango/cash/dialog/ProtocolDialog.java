package com.pinjaman.mango.cash.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.pinjaman.mango.cash.R;
import com.pinjaman.mango.cash.manager.MyApp;
import com.pinjaman.mango.cash.manager.Constant;
import com.pinjaman.mango.cash.util.SharePreUtil;


public class ProtocolDialog extends DialogFragment {

    private TextView mTvTitle;
    private WebView mWebView;
    private TextView mTvCancle;
    private TextView mTvSure;

    private String mUrl;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(null != getArguments()){
            mUrl = getArguments().getString("url");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_protocol, container, false);
        initDialog(view);
        return view;
    }

    private void initDialog(View view){
        setCancelable(false);

        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    return true;
                }else {
                    //这里注意当不是返回键时需将事件扩散，否则无法处理其他点击事件
                    return false;
                }
            }
        });

        mTvTitle = view.findViewById(R.id.tv_title);
        mWebView = view.findViewById(R.id.webView);
        mTvCancle = view.findViewById(R.id.tv_cancle);
        mTvSure = view.findViewById(R.id.tv_sure);

        initWebView();
        mWebView.loadUrl(mUrl);

        mTvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                System.exit(0);
            }
        });
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePreUtil.putBoolean(MyApp.mContext, Constant.IS_SHOW_POLICY, true);
                dismiss();
            }
        });
    }


    @SuppressWarnings("setJavaScriptEnabled")
    private void initWebView(){
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setDefaultTextEncodingName("utf-8");
        mSettings.setSupportZoom(false);
        mSettings.setBuiltInZoomControls(false);
        mSettings.setUseWideViewPort(true);
        mSettings.setNeedInitialFocus(false);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mSettings.setBlockNetworkImage(false);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }else{
            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebChromeClient(new MyWebChromeClient());
    }


    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if(null != title){
                mTvTitle.setText(title);
            }
        }
    }



}
