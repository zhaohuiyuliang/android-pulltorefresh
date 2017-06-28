package com.pull_more_refresh;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangliang on 2017/6/28.
 */

public class ControlApplication extends Application {

    private static ControlApplication instance;
    private Map<String, Bitmap> mBitmapMap;

    public static ControlApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public Map<String, Bitmap> getBitmapMap() {
        if (mBitmapMap == null) {
            mBitmapMap = new HashMap<>();
        }
        return  mBitmapMap;
    }

}
