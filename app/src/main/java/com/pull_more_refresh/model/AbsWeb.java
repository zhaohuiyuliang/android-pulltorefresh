package com.pull_more_refresh.model;

/**
 * Created by wangliang on 2017/6/27.
 */

public abstract class AbsWeb {
    private static int ID_ = 0;
    private int ID;
    private String url;
    private TYPE mTYPE;

    public AbsWeb(String url, TYPE mTYP) {
        this.url = url;
        this.mTYPE = mTYP;
        ID = ++ID_;
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


    public enum TYPE {
        BITMAP, FILE_, CONTENT
    }
}
