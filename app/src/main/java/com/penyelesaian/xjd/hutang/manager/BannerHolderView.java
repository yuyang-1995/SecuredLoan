package com.penyelesaian.xjd.hutang.manager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.penyelesaian.xjd.hutang.R;
import com.penyelesaian.xjd.hutang.banner.holder.Holder;
import com.squareup.picasso.Picasso;


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
        Picasso.get()
                .load(data)
                .placeholder(R.mipmap.icon_placeholder_banner)
                .into(imageView);
    }
}
