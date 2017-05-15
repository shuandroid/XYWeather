package com.chen.xyweather.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.model.UserHelper;
import com.chen.xyweather.model.UserInstance;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.ui.adapter.DiscoverListAdapter;
import com.chen.xyweather.ui.holder.DiscoverItemHolder;
import com.chen.xyweather.ui.recycler.RefreshableRecyclerView;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.UserCacheUtils;
import com.chen.xyweather.utils.UtilManger;
import com.chen.xyweather.view.drawer.BaseDrawer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * 发现好友
 */
public class DiscoverFragment extends BaseFragment {

    private View rootView;
    private DiscoverListAdapter<UserModel> discoverListAdapter;

    private LinearLayoutManager layoutManager;

    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefresh;

    @Bind(R.id.recycler_view)
    protected RefreshableRecyclerView mRecyclerView;

    public DiscoverFragment() {
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

        rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, rootView);

        init();
        initRecycler();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.refreshData();
    }

    private void init() {

    }

    private void initRecycler() {
        layoutManager = new LinearLayoutManager(getContext());
        discoverListAdapter = new DiscoverListAdapter<>(DiscoverItemHolder.class);
        mRecyclerView.setOnLoadDataListener(new RefreshableRecyclerView.OnLoadDataListener() {
            @Override
            public void onLoad(int skip, int limit, boolean isRefresh) {
                loadMoreDiscoverData(skip, limit, isRefresh);
            }
        });

        mRecyclerView.setRelationSwipeLayout(mSwipeRefresh);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(discoverListAdapter);
    }


    private void loadMoreDiscoverData(int skip, int limit, final boolean isRefresh) {

        AVQuery<UserModel> query = UserModel.getQuery(UserModel.class);
        UserModel user = UserModel.getCurrentUser();
        query.whereNotEqualTo("objectId", user.getObjectId());

        query.skip(skip);
        query.limit(limit);
        query.setCachePolicy(AVQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findInBackground(new FindCallback<UserModel>() {
            @Override
            public void done(List<UserModel> list, AVException e) {
                if (e == null) {
                    UserCacheUtils.cacheUsers(list);
                    mRecyclerView.setLoadComplete(list.toArray(), isRefresh);
                } else {
                    DebugLog.e("discover" + list);
                    DebugLog.e("discover e" + e);
                    UtilManger.handleError(getContext(), e.getCode());
                }
            }
        });
    }

}
