package com.damai.kekayaan.anda.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;


public class ToastUtil {

    /**
     * 中部显示
     * @param context
     * @param meassage
     */
    public static void showCenter(Context context, int meassage){
        if(meassage != 0){
            Toast toast = Toast.makeText(context, meassage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    /**
     * 中部显示
     * @param context
     * @param meassage
     */
    public static void showCenter(Context context, String meassage){
        if(!TextUtils.isEmpty(meassage)) {
            Toast toast = Toast.makeText(context, meassage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

}
