package com.pull_more_refresh.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pull_more_refresh.FileUtils;
import com.pull_more_refresh.model.BeanImp;
import com.pull_more_refresh.net.ReadWebContent;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangliang on 2017/6/28.
 */

public class TaskRunnable implements Runnable, ReadWebContent.LoadListener {
    private FileSaveListener mFileSaveListener;

    private BeanImp mWebTask;

    public TaskRunnable(BeanImp beanImp, FileSaveListener fileSaveListener) {
        this.mWebTask = beanImp;
        mFileSaveListener = fileSaveListener;
    }

    @Override
    public void run() {
        handleRequest(mWebTask);
    }

    @Override
    public void handlerInputStream(InputStream inputStream) {

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        try {
            FileUtils.saveImageToSD(inputStream, mWebTask.getFileName());
            if (mFileSaveListener != null) {
                mFileSaveListener.handlerFileSaveComplete(mWebTask);
            }
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

    public interface FileSaveListener {
        void handlerFileSaveComplete(BeanImp beanImp);
    }
}
