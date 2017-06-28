package com.pull_more_refresh.control;

import android.os.Message;

import com.pull_more_refresh.ControlApplication;
import com.pull_more_refresh.UIHandler;

/**
 * Created by wangliang on 2017/6/28.
 */

public abstract class BaseControl {

    protected UIHandler mUIHandler;

    protected ControlApplication mApplication = ControlApplication.getInstance();


    abstract class AbsHandler extends UIHandler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refreshUI(msg);
        }

        abstract void refreshUI(Message msg);
    }
}
