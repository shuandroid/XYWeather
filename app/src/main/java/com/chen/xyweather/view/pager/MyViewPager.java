package com.chen.xyweather.view.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chen
 * Data
 */
public class MyViewPager extends ViewPager {

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {

        return super.canScroll(v, checkV, dx, x, y);
    }

    @Override
    public int reDetermineTargetPage(int targetPage) {
        int rtn = targetPage;
        return rtn;
    }
}
