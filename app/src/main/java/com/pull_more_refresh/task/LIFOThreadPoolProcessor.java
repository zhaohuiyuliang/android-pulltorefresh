package com.pull_more_refresh.task;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangliang on 2017/6/28.
 */

public class LIFOThreadPoolProcessor {

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

    public Future<?> submitTask(LIFOTask lifoTask) {
        return mExecutor.submit(lifoTask);

    }

    public void clear() {
        mExecutor.purge();
    }

}
