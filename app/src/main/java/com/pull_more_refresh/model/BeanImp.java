package com.pull_more_refresh.model;

import java.io.Serializable;

/**
 * Created by wangliang on 2017/6/28.
 */

public interface BeanImp extends Serializable {
    String getUrl();

    int getID();

    AbsWeb.TYPE getTYPE();

    String getFileName();


}
