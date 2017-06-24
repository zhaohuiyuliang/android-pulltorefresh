package com.pull_more_refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by wangliang on 2017/6/24.
 */

public class AdapterPht extends BaseAdapter {
    private List<Phto> mPhtoList;
    private Context mContext;

    public AdapterPht(Context mContext, List<Phto> mPhtoList) {
        this.mContext = mContext;
        this.mPhtoList = mPhtoList;
    }

    @Override
    public int getCount() {
        return mPhtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewhold;
        if (convertView == null) {
            viewhold = new ViewHold();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view_pht, null);
            viewhold.img_pht = convertView.findViewById(R.id.img_pht);
            convertView.setTag(viewhold);
        } else {
            viewhold = (ViewHold) convertView.getTag();
        }
        viewhold.img_pht.setImageResource(R.drawable.aaa);
        return convertView;
    }

    class ViewHold {
        ImageView img_pht;
    }
}
