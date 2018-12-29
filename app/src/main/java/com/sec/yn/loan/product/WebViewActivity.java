package com.sec.yn.loan.product;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sec.yn.loan.R;
import com.sec.yn.loan.util.AppUtils;
import com.sec.yn.loan.util.LogUtil;

import java.net.URISyntaxException;

public class WebViewActivity extends AppCompatActivity {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ProgressBar mProgressBar;
    private WebView mWebview;

    private String loadUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initActivity();
    }


    private void initActivity(){

        mIvBack = findViewById(R.id.iv_back);
        mTvTitle = findViewById(R.id.tv_title);
        mProgressBar = findViewById(R.id.progressBar);
        mWebview = findViewById(R.id.webview);


        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadUrl = getIntent().getStringExtra("loadUrl");

        initWebView();
        mWebview.loadUrl(loadUrl);

    }


    @SuppressWarnings("setJavaScriptEnabled")
    private void initWebView() {
        WebSettings mSettings = mWebview.getSettings();
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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebview.setWebViewClient(new MyWebView());
        mWebview.setWebChromeClient(new MyWebChromeClient());
    }


    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setProgress(newProgress);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (null != title) {
                mTvTitle.setText(title);
            }
        }
    }


    public class MyWebView extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url2) {

            if (url2.startsWith("market://details?id=")) {
                AppUtils.jumpAppStore(WebViewActivity.this, url2, "", 2);
            }
            if (shouldOverrideUrlLoadingByApp(view, url2)) {
                return true;
            }

            return super.shouldOverrideUrlLoading(view, loadUrl);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            if (Build.VERSION.SDK_INT >= 21) {
                if (request.getUrl().toString().startsWith("market://details?id=")) {
                    AppUtils.jumpAppStore(WebViewActivity.this,
                            request.getUrl().toString(), "", 2);
                }
                if (shouldOverrideUrlLoadingByApp(view, request.getUrl().toString())) {
                    return true;
                }
            }

            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
            builder.setMessage(R.string.notification_error_ssl_cert_invalid);
            builder.setPositiveButton(getResources().getString(R.string.continues),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handler.proceed();
                        }
                    });
            builder.setNegativeButton(getResources().getString(R.string.cancle),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handler.cancel();
                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    }



    private boolean shouldOverrideUrlLoadingByApp(WebView view, String url) {
        if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
            //不处理http, https, ftp的请求
            return false;
        }
        Intent intent;
        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            intent.setComponent(null);
            startActivity(intent);
            return true;
        } catch (URISyntaxException e) {
        } catch (ActivityNotFoundException e) {
        }
        return false;
    }


}
