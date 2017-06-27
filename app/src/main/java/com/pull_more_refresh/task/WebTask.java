package com.pull_more_refresh.task;

import com.pull_more_refresh.UIHandler;

/**
 * Created by wangliang on 2017/6/27.
 */

public abstract class WebTask {
    private int ID;
    private String url;
    private UIHandler mUIHandler;
    private TYPE mTYPE;

    public WebTask(String url, TYPE mTYP,UIHandler mUIHandler ) {
        this.url = url;
        this.mTYPE = mTYP;
        this.mUIHandler = mUIHandler;
    }

    public UIHandler getUIHandler() {
        return mUIHandler;
    }

    public void setUIHandler(UIHandler UIHandler) {
        mUIHandler = UIHandler;
    }

    public TYPE getTYPE() {
        return mTYPE;
    }

    public void setTYPE(TYPE TYPE) {
        mTYPE = TYPE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    enum TYPE {
        BITMAP, FILE_, CONTENT
    }
}
