package com.rural.loans.rupiah.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;


public class AppUtils {

    /**
     * 获取APP的版本号 失败为null
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = null;

        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取APP的版本号序列  获取失败则为 0 ;
     *
     * @param context
     * @return
     */
    public static int getVersionCoder(Context context) {
        int versionCode = 0;

        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 安装APK
     * @param context  上下文
     * @param filePath apk下载的地址
     */
    public static void installAPK(Context context, String filePath){
        installAPK(context, new File(filePath));
    }

    public static void installAPK(Context context, File file){
        if(file ==null){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context,
                        context.getPackageName()+".fileProvider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /** 根据应用包名，跳转到应用市场
     *
     * @param activity    承载跳转的Activity
     * @param packageName 所需下载（评论）的应用包名
     */
    public static void shareAppShop(Activity activity, String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id="+ packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据url跳应用市场
     * @param activity
     * @param url
     * @param type
     */
    public static void jumpAppStore(Activity activity, String url, String packageName, int type){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if(type == 1){
                if(TextUtils.isEmpty(packageName)){
                    packageName = url.substring(url.lastIndexOf("?")+1);
                }
                intent.setData(Uri.parse("market://details?" + packageName));
            }else{
                intent.setData(Uri.parse(url));
            }
            activity.startActivityForResult(intent, 100);
            activity.finish();
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }

    /**
     * 根据url跳应用市场
     * @param activity
     * @param url
     */
    public static void jumpAppStore(Activity activity, String url){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.setPackage("com.android.vending");
            activity.startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }

}
