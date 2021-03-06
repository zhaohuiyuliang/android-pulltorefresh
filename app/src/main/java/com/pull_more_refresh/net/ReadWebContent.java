package com.pull_more_refresh.net;

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

    private LoadListener mLoadListener;

    public ReadWebContent(LoadListener mLoadListener) {
        this.mLoadListener = mLoadListener;
    }

    public void loadBitmap(String url) {
        loadInputStream(url);
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

    private void loadInputStream(String url) {
        URLConnection local = getURLConnection(url);
        if (local != null) {
            try {
                InputStream inputStream = local.getInputStream();
                if (mLoadListener != null) {
                    mLoadListener.handlerInputStream(inputStream);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    public interface LoadListener {
        void handlerInputStream(InputStream inputStream);
    }
}
