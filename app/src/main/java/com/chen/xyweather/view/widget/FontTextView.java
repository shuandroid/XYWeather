package com.chen.xyweather.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chen.xyweather.ui.activity.MainActivity;

/**
 * Created by chen on 17-3-22.
 * 控制字体
 */
public class FontTextView extends TextView {

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        setTypeface(MainActivity.getTypeface(context));
    }

}
