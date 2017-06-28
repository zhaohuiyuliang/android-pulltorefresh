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
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(getID()));
        String url = getUrl();
        /**图片url不存在后缀的情况*/

        int index = url.lastIndexOf("/");
        String urlBehind = url.substring(index);
        if (urlBehind.contains(".")) {
            index = urlBehind.lastIndexOf(".");
            String type = urlBehind.substring(index);
            stringBuffer.append(type);
        } else {
            stringBuffer.append(".png");
        }
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
