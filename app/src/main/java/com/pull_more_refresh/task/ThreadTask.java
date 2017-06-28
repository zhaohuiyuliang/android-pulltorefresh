package com.pull_more_refresh.task;

import android.graphics.Bitmap;

import com.pull_more_refresh.Constants;
import com.pull_more_refresh.FileUtils;
import com.pull_more_refresh.model.BeanImp;
import com.pull_more_refresh.net.ReadWebContent;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by wangliang on 2017/6/27.
 */

public class ThreadTask implements ReadWebContent.LoadListener {

    private BeanImp mWebTask;
    Runnable task = new Runnable() {
        @Override
        public void run() {
            handleRequest(mWebTask);
        }
    };
    private BitmapListener mBitmapListener;
    private static final Executor mExecutor = Executors.newFixedThreadPool(Constants.NTHREADS);

    public ThreadTask(BeanImp task, BitmapListener bitmapListener) {
        this.mWebTask = task;
        mBitmapListener = bitmapListener;
    }

    public void start() {
        mExecutor.execute(task);
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
