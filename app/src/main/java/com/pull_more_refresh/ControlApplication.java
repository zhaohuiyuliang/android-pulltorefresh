package com.pull_more_refresh;

import android.app.Application;

/**
 * Created by wangliang on 2017/6/28.
 */

public class ControlApplication extends Application {

    private static ControlApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ControlApplication getInstance() {
        return instance;
    }

}
