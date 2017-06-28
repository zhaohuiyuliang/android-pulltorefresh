package com.pull_more_refresh;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.pull_more_refresh.net.URLConstants;
import com.pull_more_refresh.task.ImageTask;
import com.pull_more_refresh.task.ThreadTask;

/**
 * Created by wangliang on 2017/6/27.
 */

public class TestAt extends BaseActivity {
    private ImageView mImageView;

    @Override
    int getLayout() {
        return R.layout.test_at;
    }

    @Override
    void initView() {
        mImageView = findView(R.id.img_);
        mUIHandler = new AHandler() {
            @Override
            void refreshUI(Message msg) {
                Bundle bundle = msg.getData();
                if  (bundle != null) {
                    Bitmap bitmap = bundle.getParcelable("bitmap");
                    mImageView.setImageBitmap(bitmap);
                }
            }
        };
        loadData();
    }


    @Override
    void loadData() {
        ImageTask imageTask = new ImageTask(URLConstants.url3, mUIHandler);
       new ThreadTask(imageTask).start();

    }
}
