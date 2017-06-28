package com.pull_more_refresh.task;

import com.pull_more_refresh.UIHandler;

/**
 * Created by wangliang on 2017/6/27.
 */

public class ImageTask extends WebTask implements TaskImp {

    public ImageTask(String url, UIHandler uiHandler) {
        super(url, TYPE.BITMAP, uiHandler);
    }

    @Override
    public String getFileName() {
        String url = getUrl();
        int index = url.lastIndexOf(".");
        String type = url.substring(index);
        return getID() + type;
    }
}
