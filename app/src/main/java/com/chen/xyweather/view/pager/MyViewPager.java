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
        // ToDo: to add your methods.
//        boolean result = super.canScroll(v, checkV, dx, x, y);
//        if (dx < 0) {
//            return true;
//        }
//        return result;
        return super.canScroll(v,checkV, dx, x, y);
    }

    @Override
    public int reDetermineTargetPage(int targetPage) {
        int rtn = targetPage;
//        int currentPage = getCurrentItem();
//        // you can add other condition in if ()
//        if (targetPage > currentPage ) {
//            rtn = currentPage;
//        }
        return rtn;
    }
}
