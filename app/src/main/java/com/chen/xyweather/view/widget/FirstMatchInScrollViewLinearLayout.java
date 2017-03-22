package com.chen.xyweather.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by chen on 17-3-22.
 * 自定义linearLayout
 */
public class FirstMatchInScrollViewLinearLayout extends LinearLayout {


    public FirstMatchInScrollViewLinearLayout(Context context) {
        super(context);
    }

    public FirstMatchInScrollViewLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FirstMatchInScrollViewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写onMeasure(), 第一个高度为scrollview的高度
     * @param widthMeasureSpec spec
     * @param heightMeasureSpec spec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //子view的数量
        if (getChildCount() > 0) {
            final ViewParent parent = getParent();
            if (parent != null && parent instanceof ScrollView) {
                final int height = ((ScrollView)parent).getMeasuredHeight();
                if (height > 0) {
                    final View firstChild = getChildAt(0);
                    LayoutParams layoutParams = (LayoutParams) firstChild.getLayoutParams();
                    layoutParams.height = height;
                    firstChild.setLayoutParams(layoutParams);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
