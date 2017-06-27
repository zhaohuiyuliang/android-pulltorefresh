package com.pull_more_refresh.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    private ReadWebContent() {

    }

    public static final ReadWebContent getInstance() {
        if (sReadWebContent == null) {
            synchronized (ReadWebContent.class) {
                if (sReadWebContent == null) {
                    sReadWebContent = new ReadWebContent();
                }
            }
        }
        return sReadWebContent;
    }

    public Bitmap loadBitmap(String url) {
        InputStream loadInputStream = loadInputStream(url);
        Bitmap bitmap = BitmapFactory.decodeStream(loadInputStream);
        return  bitmap;
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
}
