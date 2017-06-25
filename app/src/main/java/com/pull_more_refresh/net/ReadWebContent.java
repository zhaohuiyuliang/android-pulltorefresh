package com.pull_more_refresh.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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

    private synchronized String getWenContent(String url) {
        String content = null;
        try {
            URL url1 = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(60000);
            httpURLConnection.getInputStream();
            httpURLConnection.getContent();
            Map<String, List<String>> header = httpURLConnection.getHeaderFields();
        } catch (IOException e) {

        }
        return content;

    }
}
