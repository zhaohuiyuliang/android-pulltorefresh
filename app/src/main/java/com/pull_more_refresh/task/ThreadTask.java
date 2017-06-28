package com.pull_more_refresh.task;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;

import com.pull_more_refresh.FileUtils;
import com.pull_more_refresh.net.ReadWebContent;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangliang on 2017/6/27.
 */

public class ThreadTask implements ReadWebContent.LoadListener {

    private TaskImp mWebTask;
    Runnable task = new Runnable() {
        @Override
        public void run() {
            handleRequest(mWebTask);
        }
    };

    public ThreadTask(TaskImp task) {
        this.mWebTask = task;
    }

    public void start() {
        new Thread(task).start();
    }

    @Override
    public void handlerBitmap(Bitmap bitmap) {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putParcelable("bitmap", bitmap);
        message.setData(bundle);
        mWebTask.getUIHandler().sendMessage(message);
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

    private void handleRequest(TaskImp task) {
        switch (task.getTYPE()) {
            case BITMAP: {
                ReadWebContent.getInstance(this).loadBitmap(mWebTask.getUrl());
                break;
            }


        }

    }
}
