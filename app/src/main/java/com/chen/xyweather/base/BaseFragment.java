package com.chen.xyweather.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.xyweather.R;
import com.chen.xyweather.view.drawer.BaseDrawer;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }

    public abstract String getTitle();
    public abstract void onSelected();
    public abstract BaseDrawer.Type getDrawerType();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    protected void notifyActivityUpdate() {
        if (getUserVisibleHint()) {
            Activity activity = getActivity();
            if (activity != null) {
                // TODO: 17-3-22
//                ((MainActivity)activity).updateCurDrawerType();
            } else {
                //未启动
            }
        }
    }

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}