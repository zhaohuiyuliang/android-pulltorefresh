package com.pull_more_refresh.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by wangliang on 2017/6/25.
 */

public class ReadWebContent {

    private static ReadWebContent sReadWebContent;
    private LoadListener mLoadListener;

    private ReadWebContent(LoadListener mLoadListener) {
        this.mLoadListener = mLoadListener;
    }

    public static final ReadWebContent getInstance(LoadListener mLoadListener) {
        if (sReadWebContent == null) {
            synchronized (ReadWebContent.class) {
                if (sReadWebContent == null) {
                    sReadWebContent = new ReadWebContent(mLoadListener);
                }
            }
        }
        return sReadWebContent;
    }

    public void loadBitmap(String url) {
        InputStream input = loadInputStream(url);

        ByteArrayOutputStream bysStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = input.read(buffer)) > -1) {
                bysStream.write(buffer, 0, len);
            }
            bysStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bysStream.toByteArray()));
        if (mLoadListener != null) {
            mLoadListener.handlerBitmap(bitmap);
            mLoadListener.saveFile(bitmap);
            mLoadListener.saveFile(new ByteArrayInputStream(bysStream.toByteArray()));
        }
    }

    private URLConnection getURLConnection(String url) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(60000);
        } catch (IOException e) {

        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return httpURLConnection;
        }
    }

    private Map<String, List<String>> getHeander(String url) {
        URLConnection local = getURLConnection(url);
        Map<String, List<String>> header = null;
        if (local != null) {
            header = local.getHeaderFields();
        }
        return header;

    }

    private InputStream loadInputStream(String url) {
        URLConnection local = getURLConnection(url);
        InputStream inputStream = null;
        if (local != null) {
            try {
                inputStream = local.getInputStream();
            } catch (IOException e) {

            }
        }
        return inputStream;
    }

    public interface LoadListener {
        void handlerBitmap(Bitmap bitmap);

        void saveFile(Bitmap bitmap);

        void saveFile(InputStream inputStream);
    }
}
