package com.perangkat.lunak.anda.manager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.perangkat.lunak.anda.R;
import com.perangkat.lunak.anda.banner.holder.Holder;
import com.perangkat.lunak.anda.model.GlideApp;


public class BannerHolderView implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {
        GlideApp.with(context)
                .load(data)
                .placeholder(R.mipmap.icon_placeholder_banner)
                .into(imageView);
    }
}
