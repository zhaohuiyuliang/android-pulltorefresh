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


### Bitmap压缩到原来的几分之1


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





### 以后进先出方式加载图片

栈的特点，后进先出

```java
public class LIFOTask extends FutureTask<Object> implements  Comparable<LIFOTask> {

    private static int counter;


    private int priority;

    public LIFOTask(Runnable  runnable) {
        super(runnable, new Object());
        priority = counter++;

    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(@NonNull LIFOTask lifoTask) {
        return priority > lifoTask.getPriority() ? -1 : 1;
    }
    
}



```

### 使用线程池ThreadPoolExecutor


避免无限制的创建线程消耗资源
```java

    private BlockingQueue<Runnable> optToRun =
            new PriorityBlockingQueue<>(64,
                    new Comparator<Runnable>() {
                        @Override
                        public int compare(Runnable runnable, Runnable t1) {
                            if (runnable instanceof LIFOTask && t1 instanceof LIFOTask) {
                                LIFOTask l1 = (LIFOTask) runnable;
                                LIFOTask l2 = (LIFOTask) t1;
                                return l1.compareTo(l2);
                            }
                            return 0;
                        }

                    });

    private ThreadPoolExecutor mExecutor;

    public LIFOThreadPoolProcessor(int threadCount) {
        mExecutor = new ThreadPoolExecutor(threadCount, threadCount, 0, TimeUnit.SECONDS, optToRun);
    }
```

### 图片保存到SD上

```java
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

```

### LruCache缓存图片

缓存经历：以Map<String, Bitmap>方式内存缓存Bitmap，不能很好的释放内存

LruCache，设置缓存大小，释放内存由内部逻辑处理。

```java

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

```


## License
Copyright (c) 2011 [Johan Nilsson](http://markupartist.com)

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


