package com.chen.xyweather.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.chen.xyweather.utils.StatusNetAsyncTask;
import com.chen.xyweather.utils.StatusUtils;
import com.chen.xyweather.ui.xlist.XListView;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhoutao on 17/5/11.
 *
 */
public class BaseListView<T> extends XListView implements XListView.IXListViewListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
  private static final int ONE_PAGE_SIZE = 15;
  BaseListAdapter<T> adapter;
  DataInterface<T> dataInterface = new DataInterface<T>();
  private boolean toastIfEmpty = true;

  public BaseListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void init(DataInterface<T> dataInterface, BaseListAdapter<T> adapter) {
    this.dataInterface = dataInterface;
    this.adapter = adapter;
    setAdapter(adapter);
    setXListViewListener(this);
    setOnItemClickListener(this);
    setOnItemLongClickListener(this);
    setPullLoadEnable(true);
    setPullRefreshEnable(true);
  }

  public static class DataInterface<T> {
    public List<T> getDatas(int skip, int limit, List<T> currentDatas) throws Exception {
      return new ArrayList<T>();
    }

    public void onItemSelected(T item) {
    }

    public void onItemLongPressed(T item) {
    }
  }

  @Override
  public void onRefresh() {
    loadDatas(false);
  }

  public void loadDatas(final boolean loadMore) {
    final int skip;
    if (loadMore) {
      skip = adapter.getCount();
    } else {
      if (getPullRefreshing() == false) {
        pullRefreshing();
      }
      skip = 0;
    }
    new StatusNetAsyncTask(getContext()) {
      List<T> datas;

      @Override
      protected void doInBack() throws Exception {
        if (dataInterface != null) {
          datas = dataInterface.getDatas(skip, ONE_PAGE_SIZE, adapter.getDatas());
        } else {
          datas = new ArrayList<T>();
        }
      }

      @Override
      protected void onPost(Exception e) {
        if (e != null) {
          e.printStackTrace();
          StatusUtils.toast(getContext(), e.getMessage());
        } else {
          if (loadMore == false) {
            stopRefresh();
            adapter.setDatas(datas);
            adapter.notifyDataSetChanged();
            if (datas.size() < ONE_PAGE_SIZE) {
              if (isToastIfEmpty()) {
                if (datas.size() == 0) {
                  StatusUtils.toast(getContext(), "无数据");
                }
              }
              setPullLoadEnable(false);
            } else {
              setPullLoadEnable(true);
            }
          } else {
            stopLoadMore();
            adapter.addAll(datas);
            if (datas.size() == 0) {
              StatusUtils.toast(getContext(), "没有更多了");
            }
          }
        }
      }
    }.execute();
  }

  @Override
  public void onLoadMore() {
    loadDatas(true);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    T item = (T) parent.getAdapter().getItem(position);
    if (dataInterface != null) {
      dataInterface.onItemSelected(item);
    }
  }


  @Override
  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    T item = (T) parent.getAdapter().getItem(position);
    if (dataInterface != null) {
      dataInterface.onItemLongPressed(item);
    }
    return false;
  }


  public boolean isToastIfEmpty() {
    return toastIfEmpty;
  }

  public void setToastIfEmpty(boolean toastIfEmpty) {
    this.toastIfEmpty = toastIfEmpty;
  }
}
