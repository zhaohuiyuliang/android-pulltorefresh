package com.pull_more_refresh.task;

import com.pull_more_refresh.UIHandler;

/**
 * Created by wangliang on 2017/6/28.
 */

public interface TaskImp {
    String getUrl();

    int getID();

    WebTask.TYPE getTYPE();

    UIHandler getUIHandler();

    String getFileName();


}
