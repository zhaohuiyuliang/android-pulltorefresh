package com.pull_more_refresh;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangliang on 2017/6/28.
 */

public class FileUtils {


    public static boolean saveImageToSD(Bitmap bitmap, String fileName) throws IOException {
        File sdFile = getSDDataPath();
        String path = sdFile.getAbsolutePath() + File.separator + "images" + File.separator + fileName;
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        return true;
    }

    public static synchronized boolean saveImageToSD(InputStream inputStream, String fileName) throws IOException {
        File sdFile = getSDDataPath();
        String path = sdFile.getAbsolutePath() + File.separator + Constants.IMAGE_PATH + File.separator + fileName;
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        byte[] bytes = new byte[1024];
        int len;
        InputStream input = inputStream;
        while ((len = input.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        outputStream.flush();
        return true;
    }

    public static File getSDDataPath() {
        File sdFile = ControlApplication.getInstance().getExternalFilesDir(null);
        return sdFile;
    }

    public static String imagesPath() {
        String path = getSDDataPath() + Constants.IMAGES_PATH;
        return path;
    }
}
