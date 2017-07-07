package com.pull_more_refresh.task;

import android.support.annotation.NonNull;

import java.util.concurrent.FutureTask;

/**
 * Created by wangliang on 2017/6/27.
 */

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
