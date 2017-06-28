package com.pull_more_refresh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pull_more_refresh.R;
import com.pull_more_refresh.control.ImageControl;
import com.pull_more_refresh.model.ImageBean;

import java.util.List;

/**
 * Created by wangliang on 2017/6/24.
 */

public class AdapterImg extends AbsBaseAdapter {
    private List<ImageBean> mImageOjList;
    private Context mContext;
    private ImageControl mImageControl;

    public AdapterImg(Context mContext, List<ImageBean> imageOjList) {
        this.mContext = mContext;
        this.mImageOjList = imageOjList;
        mImageControl = new ImageControl();

    }

    @Override
    public int getCount() {
        return mImageOjList.size();
    }

    @Override
    public Object getItem(int position) {
        return mImageOjList.get(position);
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
        ImageBean imageOj = mImageOjList.get(position);
        viewhold.img_pht.setTag(imageOj.getID());
        mImageControl.loadImage(imageOj, viewhold.img_pht);
        return convertView;
    }

    class ViewHold {
        ImageView img_pht;
    }


}
