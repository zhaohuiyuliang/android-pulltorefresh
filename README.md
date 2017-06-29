# 下拉刷新

![下拉刷新](https://github.com/zhaohuiyuliang/android-pulltorefresh/blob/master/raw/master/pull_refresh.png)

# 上拉加载更多
![下拉加载更多](https://github.com/zhaohuiyuliang/android-pulltorefresh/blob/master/raw/master/more_refresh.png)

Repository at <https://github.com/zhaohuiyuliang/android-pulltorefresh>.

## 使用方式

### 布局文件

``` xml
<!--
  The SuperRefreshLayout replaces a standard ListView widget.
-->
    <com.pull_more_refresh.customview.SuperRefreshLayout
        android:id="@+id/superRefreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.pull_more_refresh.customview.PullListView
            android:id="@+id/listView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scrollbars="none"
            android:dividerHeight="1px"/>
    </com.pull_more_refresh.customview.SuperRefreshLayout>
```

### 加载并保存图片

``` java

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
    
    @Override
    public void handlerInputStream(InputStream inputStream) {
        try {
            FileUtils.saveImageToSD(inputStream, mWebTask.getFileName());
            if (mFileSaveListener != null) {
                mFileSaveListener.handlerFileSaveComplete(mWebTask);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
```

###  Bitmap压缩到原来的几分之1


压缩到1/4大小，压缩过程每隔3个像素读取一个像素。图片大小及尺寸大小也变为原来1/4.

![in_sample_size_theory](https://github.com/zhaohuiyuliang/android-pulltorefresh/blob/master/raw/master/in_sample_size_theory.png)

![sample](https://github.com/zhaohuiyuliang/android-pulltorefresh/blob/master/raw/master/in_sample_size.png)

```java
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
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            bitmap = BitmapFactory.decodeFile(filePath, options);
        }
        return bitmap;
    }
    ```
###  Bitmap压缩到2的幂的规格

![scaled_theory](https://github.com/zhaohuiyuliang/android-pulltorefresh/blob/master/raw/master/scaled_theory.png)

![sample_scaled](https://github.com/zhaohuiyuliang/android-pulltorefresh/blob/master/raw/master/sample_scaled.png)

### 使用线程池ThreadPoolExecutor

避免无限制的创建线程消耗资源

### 后进先出原则

### 

It's possible to add a last updated time using the method `setLastUpdated`
and `onRefreshComplete`. The text provided to these methods will be set below
the Release to refresh text. Note that the time representation is not validated
replaces the previous text, which means that it's possible and recommended to
add a text similar to "Last Update: 15:23". This might be changed in future
versions.

## 1.5 Support

To use the widget on 1.5 the necessary drawables needs to be copied to that
projects drawable folder. The drawables needed by the widget can be found in
the drawable-hdpi folder in the library project.

## Contributors

* [Jason Knight](http://www.synthable.com/) - <https://github.com/synthable>
* [Eddie Ringle](http://eddieringle.com/) - <https://github.com/eddieringle>
* [Christof Dorner](http://chdorner.com) - <https://github.com/chdorner>
* [Olof Brickarp](http://www.yay.se) - <https://github.com/coolof>
* [James Smith](http://loopj.com/) - <https://github.com/loopj>
* [Alex Volovoy](http://bytesharp.com/) - <https://github.com/avolovoy>
* Bo Maryniuk
* [kidfolk](https://github.com/kidfolk)
* [Tim Mahoney](https://github.com/timahoney)
* [Richard Guest](https://github.com/quiffman)

## Are you using this widget?

If you are using this widget please feel free to add your app to the
[wiki](https://github.com/johannilsson/android-pulltorefresh/wiki/Apps).

## License
Copyright (c) 2011 [Johan Nilsson](http://markupartist.com)

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


