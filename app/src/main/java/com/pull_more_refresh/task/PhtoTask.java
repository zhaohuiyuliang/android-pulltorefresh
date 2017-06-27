package com.pull_more_refresh.task;

import com.pull_more_refresh.UIHandler;

/**
 * Created by wangliang on 2017/6/27.
 */

public class PhtoTask extends WebTask {
    public PhtoTask(String url, UIHandler uiHandler) {
        super(url, TYPE.BITMAP, uiHandler);
    }
}
