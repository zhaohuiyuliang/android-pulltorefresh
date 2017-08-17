package com.pull_more_refresh.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.pull_more_refresh.Constants;
import com.pull_more_refresh.ControlApplication;
import com.pull_more_refresh.FileUtils;
import com.pull_more_refresh.adapter.AbsBaseAdapter;
import com.pull_more_refresh.model.BeanImp;
import com.pull_more_refresh.model.ImageBean;
import com.pull_more_refresh.net.URLConstants;
import com.pull_more_refresh.task.LIFOTask;
import com.pull_more_refresh.task.LIFOThreadPoolProcessor;
import com.pull_more_refresh.task.TaskRunnable;

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

public class AsyncImageLoader extends BaseControl implements TaskRunnable.FileSaveListener {
    private Map<String, Bitmap> bitmapMap;
    private AbsBaseAdapter mAbsBaseAdapter;
    private LIFOThreadPoolProcessor mPoolProcessor;

    public AsyncImageLoader(AbsBaseAdapter mAbsBaseAdapter) {
        this.mAbsBaseAdapter = mAbsBaseAdapter;
        mPoolProcessor = new LIFOThreadPoolProcessor(50);
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
                        ImageBean imageBean = (ImageBean) bundle.getSerializable(Constants.KEY_BEAN_IMP);

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
                Bitmap bitmap = getCompressBitmap(imageBean);
                img_pht.setImageBitmap(bitmap);
                bitmapMap.put(String.valueOf(imageBean.getID()), bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                img_pht.setImageBitmap(null);
                mPoolProcessor.submitTask(new LIFOTask(new TaskRunnable(imageBean, this)));
            }
        } else {
            img_pht.setImageBitmap(null);
            mPoolProcessor.submitTask(new LIFOTask(new TaskRunnable(imageBean, this)));
        }
    }


    private boolean isSaveImageInSD(String fileName) {
        String filePath = FileUtils.imagesPath() + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 得到原始大小的位图
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private Bitmap getBitmapFromSD(String fileName) throws IOException {
        Bitmap bitmap = null;
        String filePath = FileUtils.imagesPath() + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            InputStream inputStream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        return bitmap;
    }

    @Override
    public void handlerFileSaveComplete(BeanImp beanImp) {
        Message message = Message.obtain();
        try {
            Bitmap bitmap = getCompressBitmap(beanImp);
            /**对图片进行压缩*/
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.KEY_BITMAP, bitmap);
            bundle.putSerializable(Constants.KEY_BEAN_IMP, beanImp);
            message.setData(bundle);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mUIHandler.sendMessage(message);
        }
    }

    /**
     * 得到压缩后的位图
     *
     * @param beanImp
     * @return
     */
    private Bitmap getCompressBitmap(BeanImp beanImp) throws IOException {
        Bitmap bitmap = null;
        String filePath = FileUtils.imagesPath() + beanImp.getFileName();
        File file = new File(filePath);
        if (file.exists()) {
            bitmap = getScaled(filePath);
//            bitmap = getZoomBitmap(bitmap);
        }
        return bitmap;
    }

    private Bitmap getScaled(String filePath) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    /**
     * 图片剪切
     *
     * @param paramBitmap
     * @return
     */
    private Bitmap getZoomBitmap(Bitmap paramBitmap) {
        int screenW = ControlApplication.getInstance().getScreenWidth();

        int s = (screenW * 2 / 3 - 20);
        Bitmap localBitmap = null;
        try {
            int width = paramBitmap.getWidth();
            int height = paramBitmap.getHeight();
            // 图片小于xx，等比例拉伸再剪切
            if (width < s || height < 160) {
                float scaleWidth = ((float) s) / width;
                float scaleHeight = ((float) 160) / height;
                float scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                Bitmap newbm = Bitmap.createBitmap(paramBitmap, 0, 0, width, height, matrix, true);
                localBitmap = Bitmap.createBitmap(newbm, 0, 0, s, 160);
            } else {
                localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, s, 160);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localBitmap;
    }

}
