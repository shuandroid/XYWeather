package com.chen.xyweather.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.view.drawer.BaseDrawer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingFragment extends BaseFragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public BaseDrawer.Type getDrawerType() {
        return null;
    }


    @Bind(R.id.s_notice)
    protected RelativeLayout tv_notice;

    @Bind(R.id.s_changeskin)
    protected RelativeLayout tv_changeskin;

    @Bind(R.id.s_cleardata)
    protected RelativeLayout tv_cleardata;

    @Bind(R.id.s_about)
    protected RelativeLayout tv_about;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, null);

        ButterKnife.bind(this, view);

        init(view);

        return view;
    }

    /**
     * 初始化setting 里的view,设置监听
     * @param view view
     */
    private void init(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.s_notice:
                        break;
                    case R.id.s_changeskin:
                        break;
                    case R.id.s_cleardata:
                        break;
                    case R.id.s_about:
                        Intent intent = new Intent(getActivity(), MapActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
