package com.pull_more_refresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LoadPhtActivity extends AppCompatActivity {


    protected ListView mListView;

    protected  AdapterPht mAdapterPht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pht);
        initWidget();
    }

    public void initWidget() {
        List<Phto> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Phto());
        }
        mAdapterPht = new AdapterPht(this, list);
        mListView = findView(R.id.listView);
        mListView.setAdapter(mAdapterPht);

    }

    protected <T extends View> T findView(int res_id) {
        return (T) findViewById(res_id);
    }
}
