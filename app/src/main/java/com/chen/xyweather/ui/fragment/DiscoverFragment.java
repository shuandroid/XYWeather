package com.chen.xyweather.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.ui.adapter.DiscoverListAdapter;
import com.chen.xyweather.ui.holder.DiscoverItemHolder;
import com.chen.xyweather.view.drawer.BaseDrawer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends BaseFragment {

    private View rootView;
    private DiscoverListAdapter<UserModel> discoverListAdapter;

    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefresh;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    public DiscoverFragment() {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, rootView);

        init();
        initRecycler();
        return rootView;
    }

    private void init() {

    }

    private void initRecycler() {
        discoverListAdapter = new DiscoverListAdapter<>(DiscoverItemHolder.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(discoverListAdapter);
    }

}
