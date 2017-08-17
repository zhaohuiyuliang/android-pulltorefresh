package com.pull_more_refresh.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.pull_more_refresh.R;
import com.pull_more_refresh.adapter.AdapterImg;

import static com.pull_more_refresh.control.AsyncImageLoader.getTestData;

public class LoadImgActivity extends AppCompatActivity {


    protected ListView mListView;

    protected AdapterImg mAdapterImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pht);
        initWidget();
    }

    public void initWidget() {

        mAdapterImg = new AdapterImg(this, getTestData());
        mListView = findView(R.id.listView);
        mListView.setAdapter(mAdapterImg);

    }

    protected <T extends View> T findView(int res_id) {
        return (T) findViewById(res_id);
    }
}
