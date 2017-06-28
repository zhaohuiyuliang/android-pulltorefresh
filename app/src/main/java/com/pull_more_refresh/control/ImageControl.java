package com.pull_more_refresh.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.pull_more_refresh.Constants;
import com.pull_more_refresh.FileUtils;
import com.pull_more_refresh.adapter.AbsBaseAdapter;
import com.pull_more_refresh.model.BeanImp;
import com.pull_more_refresh.model.ImageBean;
import com.pull_more_refresh.net.URLConstants;
import com.pull_more_refresh.task.ThreadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangliang on 2017/6/28.
 */

public class ImageControl extends BaseControl implements ThreadTask.BitmapListener {
    private Map<String, Bitmap> bitmapMap;
    private AbsBaseAdapter mAbsBaseAdapter;

    public ImageControl(AbsBaseAdapter mAbsBaseAdapter) {
        this.mAbsBaseAdapter = mAbsBaseAdapter;
    }

    public static List<ImageBean> getTestData() {
        List<String> list = URLConstants.getUrls();
        List<ImageBean> imageBeanList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            imageBeanList.add(new ImageBean(list.get(i)));
        }
        return imageBeanList;
    }

    public void loadImage(final ImageBean imageBean, final ImageView img_pht) {
        if (mUIHandler == null) {
            mUIHandler = new AbsHandler() {
                @Override
                void refreshUI(Message msg) {
                    Bundle bundle = msg.getData();

                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable(Constants.KEY_BITMAP);
                        ImageBean imageBean = (ImageBean) bundle.getSerializable(Constants.KEY_BEANIMP);

                        bitmapMap.put(String.valueOf(imageBean.getID()), bitmap);
                        mAbsBaseAdapter.notifyDataSetChanged();
                    }
                }
            };
            bitmapMap = mApplication.getBitmapMap();
        }

        if (bitmapMap.containsKey(String.valueOf(imageBean.getID()))) {/**检查内存*/
            Bitmap bitmap = bitmapMap.get(String.valueOf(imageBean.getID()));
            img_pht.setImageBitmap(bitmap);
        } else if (isSaveImageInSD(imageBean.getFileName())) {/**检查SD卡*/
            try {
                Bitmap bitmap = getBitmapFromSD(imageBean.getFileName());
                img_pht.setImageBitmap(bitmap);
                bitmapMap.put(String.valueOf(imageBean.getID()), bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                img_pht.setImageBitmap(null);
                new ThreadTask(imageBean).start();
            }
        } else {
            img_pht.setImageBitmap(null);
            new ThreadTask(imageBean).start();
        }
    }


    private boolean isSaveImageInSD(String fileName) {
        String filePath = FileUtils.getSDDataPath() + File.separator + Constants.IMAGE_PATH + File.separator + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    private Bitmap getBitmapFromSD(String fileName) throws IOException {
        Bitmap bitmap = null;
        String filePath = FileUtils.getSDDataPath() + File.separator + Constants.IMAGE_PATH + File.separator + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            InputStream inputStream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        return bitmap;
    }

    @Override
    public void handlerBitmap(Bitmap bitmap, BeanImp beanImp) {
        /**对图片进行压缩*/
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_BITMAP, bitmap);
        bundle.putSerializable(Constants.KEY_BEANIMP, beanImp);
        message.setData(bundle);
        mUIHandler.sendMessage(message);
    }
}
