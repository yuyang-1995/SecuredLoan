package com.pecah.kan.uangnya.util;

import android.util.Log;

import com.pecah.kan.uangnya.BuildConfig;


public class LogUtil {

    private LogUtil()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static boolean isDebug = BuildConfig.is_log;
    private static final String TAG = "DEBUG_TAG";
    private static int LOG_MAXLENGTH = 2000;


    public static void i(String msg)
    {
        if (isDebug){
            logI(msg);
        }

    }

    public static void d(String msg)
    {
        if (isDebug){
            logD(msg);
        }
    }

    public static void e(String msg)
    {
        if (isDebug){
            logE(msg);
        }
    }

    public static void v(String msg)
    {
        if (isDebug){
            logV(msg);
        }
    }

    public static void logI(String msg){
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            if (strLength > end) {
                Log.i(TAG, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.i(TAG, msg.substring(start, strLength));
                break;
            }
        }
    }

    public static void logD(String msg){
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            if (strLength > end) {
                Log.d(TAG, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.d(TAG, msg.substring(start, strLength));
                break;
            }
        }
    }

    public static void logV(String msg){
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            if (strLength > end) {
                Log.v(TAG, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.v(TAG, msg.substring(start, strLength));
                break;
            }
        }
    }

    public static void logE(String msg){
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            if (strLength > end) {
                Log.e(TAG, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e(TAG, msg.substring(start, strLength));
                break;
            }
        }
    }

}

