package com.pull_more_refresh;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.pull_more_refresh.net.ReadWebContent;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ReadWebContent.getInstance().loadBitmap("http://img.sj33.cn/uploads/allimg/201302/1-130201105055.jpg");
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putParcelable("bitmap", bitmap);
                message.setData(bundle);
                mUIHandler.sendMessage(message);
            }
        }).start();

    }
}
