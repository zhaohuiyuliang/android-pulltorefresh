package com.pull_more_refresh.task;

import android.graphics.Bitmap;

import com.pull_more_refresh.FileUtils;
import com.pull_more_refresh.model.BeanImp;
import com.pull_more_refresh.net.ReadWebContent;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangliang on 2017/6/28.
 */

public class TaskR implements Runnable, ReadWebContent.LoadListener {
    private BitmapListener mBitmapListener;

    private BeanImp mWebTask;

    public TaskR(BeanImp beanImp, BitmapListener bitmapListener) {
        this.mWebTask = beanImp;
        mBitmapListener = bitmapListener;
    }

    @Override
    public void run() {
        handleRequest(mWebTask);
    }

    @Override
    public void handlerBitmap(Bitmap bitmap) {
        if (mBitmapListener != null) {
            mBitmapListener.handlerBitmap(bitmap, mWebTask);
        }
    }

    @Override
    public void saveFile(Bitmap bitmap) {
//        try {
//            FileUtils.saveImageToSD(bitmap, mWebTask.getFileName());
//        } catch (IOException e) {
//
//        }
    }


    @Override
    public void saveFile(InputStream inputStream) {
        try {
            FileUtils.saveImageToSD(inputStream, mWebTask.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(BeanImp task) {
        switch (task.getTYPE()) {
            case BITMAP: {
                new ReadWebContent(this).loadBitmap(mWebTask.getUrl());
                break;
            }
        }

    }

    public interface BitmapListener {
        void handlerBitmap(Bitmap bitmap, BeanImp beanImp);
    }
}
