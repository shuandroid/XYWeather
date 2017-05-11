package com.chen.xyweather.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import cn.leancloud.chatkit.adapter.LCIMCommonListAdapter;
import cn.leancloud.chatkit.viewholder.LCIMCommonViewHolder;

/**
 * Created by chen on 17-5-11.
 * adapter 装载发现联系人的adapter
 */

public class DiscoverListAdapter<T> extends LCIMCommonListAdapter<T> {

    public static final int HEADER_ITEM_TYPE = -1;
    public static final int FOOTER_ITEM_TYPE = -2;
    public static final int COMMON_ITEM_TYPE = 1;

    private View headerView = null;
    private View footerView = null;


    public DiscoverListAdapter(Class<?> vhClass) {
        super(vhClass);
    }


    public void setHeaderView(View view) {
        headerView = view;
    }

    public void setFooterView(View view) {
        footerView = view;
    }

    @Override
    public int getItemCount() {
        int itemCount = super.getItemCount();
        if (null != headerView) {
            ++itemCount;
        }
        if (null != footerView) {
            ++itemCount;
        }
        return itemCount;
    }

    @Override
    public long getItemId(int position) {
        if (null != headerView && 0 == position) {
            return -1;
        }

        if (null != footerView && position == getItemCount() - 1) {
            return -2;
        }
        return super.getItemId(position - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (null != headerView && 0 == position) {
            return HEADER_ITEM_TYPE;
        }

        if (null != footerView && position == getItemCount() - 1) {
            return FOOTER_ITEM_TYPE;
        }

        return COMMON_ITEM_TYPE;
    }

    @Override
    public void onBindViewHolder(LCIMCommonViewHolder holder, int position) {
        int truePosition = position;
        if (null != headerView) {
            if (0 == position) {
                return;
            } else {
                truePosition = position - 1;
            }
        }

        if (null != footerView && position == getItemCount() - 1) {
            return;
        }
        super.onBindViewHolder(holder, truePosition);
    }

    @Override
    public LCIMCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (HEADER_ITEM_TYPE == viewType) {
            //后面扩展
        }

        if (FOOTER_ITEM_TYPE == viewType) {
            //
        }
        return super.onCreateViewHolder(parent, viewType);
    }
}

