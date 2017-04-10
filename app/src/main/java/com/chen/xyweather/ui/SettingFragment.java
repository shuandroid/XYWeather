package com.chen.xyweather.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.view.drawer.BaseDrawer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
    protected TextView tv_notice;

    @Bind(R.id.s_changeskin)
    protected TextView tv_changeskin;

    @Bind(R.id.s_cleardata)
    protected TextView tv_cleardata;

    @Bind(R.id.s_about)
    protected TextView tv_about;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ButterKnife.bind(this, view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.s_notice:
                        break;
                    case R.id.s_changeskin:
                        break;
                    case R.id.s_cleardata:
                        break;
                    case R.id.s_about:
                        Intent intent=new Intent(getActivity(),MapActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

}
