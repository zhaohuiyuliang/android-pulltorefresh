package com.pull_more_refresh.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pull_more_refresh.R;

/**
 * Created by wangliang on 2017/6/24.
 */

public class PullListView extends ListView {
    private TextView mFooterText;

    private ProgressBar mFooterProgressBar;

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFooter();
    }

    private void createFooter() {
        View mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_view_footer, null);
        mFooterText = mFooterView.findViewById(R.id.tv_footer);
        mFooterProgressBar = mFooterView.findViewById(R.id.pb_footer);
        mFooterView.setVisibility(View.VISIBLE);
        addFooterView(mFooterView);
    }


    protected void setFooterType(TYPE_MORE type) {
        switch (type) {
            case TYPE_NORMAL:
            case TYPE_LOADING: {
                mFooterText.setText(getResources().getString(R.string.footer_type_loading));
                mFooterProgressBar.setVisibility(View.VISIBLE);
                break;
            }
            case TYPE_NET_ERROR: {
                mFooterText.setText(getResources().getString(R.string.footer_type_net_error));
                mFooterProgressBar.setVisibility(View.GONE);
                break;
            }
            case TYPE_ERROR: {
                mFooterText.setText(getResources().getString(R.string.footer_type_error));
                mFooterProgressBar.setVisibility(View.GONE);
                break;
            }
            case TYPE_NO_MORE: {
                mFooterText.setText(getResources().getString(R.string.footer_type_not_more));
                mFooterProgressBar.setVisibility(View.GONE);
                break;
            }
            case TYPE_SERVER_FAIL: {
                mFooterText.setText(getResources().getString(R.string.footer_type_server_fail));
                mFooterProgressBar.setVisibility(View.GONE);
                break;
            }
            case TYPE_HIDE: {
                mFooterText.setVisibility(View.GONE);
                mFooterProgressBar.setVisibility(View.GONE);
                break;
            }
        }
    }

    enum TYPE_MORE {
        TYPE_NORMAL,
        TYPE_LOADING,
        TYPE_NO_MORE,
        TYPE_ERROR,
        TYPE_NET_ERROR,
        TYPE_HIDE,
        TYPE_SERVER_FAIL
    }

}
