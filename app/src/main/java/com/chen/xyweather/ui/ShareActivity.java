package com.chen.xyweather.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.bean.Pictures;
import com.chen.xyweather.utils.GridAdapter;
import com.chen.xyweather.utils.MyOkhttp;
import com.chen.xyweather.utils.SnackbarUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShareActivity extends BaseActivity {

    private static RecyclerView recyclerview;
    private LinearLayout coordinatorLayout;
    private GridAdapter mAdapter;
    private List<Pictures> mPictures;
    private StaggeredGridLayoutManager mLayoutManager;
    private int lastVisibleItem ;
    private int page=1;
    private ItemTouchHelper itemTouchHelper;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);

        initView();
        setListener();

        new GetData().execute("http://gank.io/api/data/福利/10/1");
    }

    private void initView(){
        coordinatorLayout=(LinearLayout) findViewById(R.id.layout);

        recyclerview=(RecyclerView)findViewById(R.id.staggered_recycler);
        mLayoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(mLayoutManager);

        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.staggered_swipe_refresh) ;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

    }

    private void setListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                new GetData().execute("http://gank.io/api/data/福利/10/1");
            }
        });

        itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags=0;
                if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                    dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlags,0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from=viewHolder.getAdapterPosition();
                int to=target.getAdapterPosition();
                Pictures moveItem=mPictures.get(from);
                mPictures.remove(from);
                mPictures.add(to,moveItem);
                mAdapter.notifyItemMoved(from,to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
        });

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=mLayoutManager.getItemCount()) {
                    new GetData().execute("http://gank.io/api/data/福利/10/"+(++page));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] positions= mLayoutManager.findLastVisibleItemPositions(null);
                lastVisibleItem = Math.max(positions[0],positions[1]);
            }
        });
    }

    private class GetData extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {

            return MyOkhttp.get(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(!TextUtils.isEmpty(result)){

                JSONObject jsonObject;
                Gson gson=new Gson();
                String jsonData=null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(mPictures==null||mPictures.size()==0){
                    mPictures= gson.fromJson(jsonData, new TypeToken<List<Pictures>>() {}.getType());
                    Pictures pages=new Pictures();
                    pages.setPage(page);
                    mPictures.add(pages);
                }else{
                    List<Pictures> more= gson.fromJson(jsonData, new TypeToken<List<Pictures>>() {}.getType());
                    mPictures.addAll(more);
                    Pictures pages=new Pictures();
                    pages.setPage(page);
                    mPictures.add(pages);
                }

                if(mAdapter==null){
                    recyclerview.setAdapter(mAdapter = new GridAdapter(ShareActivity.this,mPictures));

                    //设置点击监听
                    mAdapter.setOnItemClickListener(new GridAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view) {
                            int position=recyclerview.getChildAdapterPosition(view);
                            SnackbarUtil.ShortSnackbar(coordinatorLayout,"点击第"+position+"个",SnackbarUtil.Info).show();
                        }

                        @Override
                        public void onItemLongClick(View view) {
                            itemTouchHelper.startDrag(recyclerview.getChildViewHolder(view));
                        }
                    });

                    itemTouchHelper.attachToRecyclerView(recyclerview);
                }else{
                    mAdapter.notifyDataSetChanged();
                }
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setupActionbar() {

    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
