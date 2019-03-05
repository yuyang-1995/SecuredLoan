package com.damai.kekayaan.anda.manager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.damai.kekayaan.anda.R;
import com.damai.kekayaan.anda.banner.holder.Holder;
import com.damai.kekayaan.anda.model.GlideApp;


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
