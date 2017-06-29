package com.pull_more_refresh;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangliang on 2017/6/28.
 */

public class ControlApplication extends Application {

    private static ControlApplication instance;
    private Map<String, Bitmap> mBitmapMap;
    private int screenWidth;
    private int screenHeight;

    public static ControlApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DisplayMetrics dm = getResources().getDisplayMetrics();

        screenWidth = dm.widthPixels;// 屏幕宽（像素，如：480px）

        screenHeight = dm.heightPixels;// 屏幕高（像素，如：800px）
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public Map<String, Bitmap> getBitmapMap() {
        if (mBitmapMap == null) {
            mBitmapMap = new HashMap<>();
        }
        return mBitmapMap;
    }

}
