package com.chen.xyweather.ui.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.xyweather.R;

/**
 * Created by chen on 17-5-12.
 * test
 */

public class LoadMoreFooterView  extends LinearLayout{

    private Context mContext;
    private View mProgressBar;
    private TextView mHintView;

    public LoadMoreFooterView(Context context) {
        super(context);
        initView(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.load_more_footer_layout, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

        mProgressBar = moreView.findViewById(R.id.load_more_footer_progressbar);
        mHintView = (TextView) moreView.findViewById(R.id.load_more_footer_hint_textview);
    }

    public void onLoadStatusChanged(int staus) {
        if (staus == RefreshableRecyclerView.STATUS_LAOD_MORE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mHintView.setVisibility(GONE);
        } else {
            mProgressBar.setVisibility(GONE);
            mHintView.setVisibility(View.VISIBLE);
        }
    }
}
