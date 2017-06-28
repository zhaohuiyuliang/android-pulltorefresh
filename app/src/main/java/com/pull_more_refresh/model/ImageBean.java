package com.pull_more_refresh.model;

/**
 * Created by wangliang on 2017/6/27.
 */

public class ImageBean extends AbsWeb implements BeanImp {


    public ImageBean(String url) {
        super(url, TYPE.BITMAP);
    }


    @Override
    public String getFileName() {
        StringBuffer stringBuffer = new StringBuffer(getID());
        String url = getUrl();
        int index = url.lastIndexOf(".");
        String type = url.substring(index);
        stringBuffer.append(type);
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImageBean) {
            if (((ImageBean) obj).getID() == getID()) {
                return true;
            }
        }
        return super.equals(obj);
    }
}
