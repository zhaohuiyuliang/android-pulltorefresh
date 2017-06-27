package com.pull_more_refresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by wangliang on 2017/6/27.
 */

public abstract class BaseActivity extends Activity {
    protected UIHandler mUIHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
    }

    abstract int getLayout();

    abstract void initView();

    abstract void loadData();

    protected <T extends View> T findView(int res_id) {
        return (T) findViewById(res_id);
    }

    protected abstract class AHandler extends UIHandler {
        public AHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refreshUI(msg);
        }

        abstract void refreshUI(Message msg);

    }
}
