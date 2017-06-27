package com.pull_more_refresh.task;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;

import com.pull_more_refresh.net.ReadWebContent;

/**
 * Created by wangliang on 2017/6/27.
 */

public class ThreadTask {

    private WebTask mWebTask;
    Runnable task = new Runnable() {
        @Override
        public void run() {
            handleRequest(mWebTask);
        }
    };

    public ThreadTask(WebTask task) {
        this.mWebTask = task;
    }

    public void start() {
        new Thread(task).start();
    }

    private void handleRequest(WebTask task) {
        switch (task.getTYPE()) {
            case BITMAP:{
                Bitmap bitmap = ReadWebContent.getInstance().loadBitmap(mWebTask.getUrl());
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putParcelable("bitmap", bitmap);
                message.setData(bundle);
                mWebTask.getUIHandler().sendMessage(message);
                break;
            }



        }

    }
}
