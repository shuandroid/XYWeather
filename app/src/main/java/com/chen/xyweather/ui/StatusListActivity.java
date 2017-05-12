package com.chen.xyweather.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseListView;
import com.chen.xyweather.bean.Status;
import com.chen.xyweather.ui.adapter.StatusListAdapter;
import com.chen.xyweather.utils.App;
import com.chen.xyweather.utils.StatusNetAsyncTask;
import com.chen.xyweather.utils.StatusService;
import com.chen.xyweather.utils.StatusUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusListActivity extends BaseActivity {

    private static final int SEND_REQUEST = 2;

    @Bind(R.id.status_List)
    protected BaseListView<Status> statusList;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);
        App.regiserUser(AVUser.getCurrentUser());
        initList();
        statusList.setToastIfEmpty(false);
        statusList.onRefresh();
    }
    private void initList() {
        statusList.init(new BaseListView.DataInterface<Status>() {
            @Override
            public List<Status> getDatas(int skip, int limit, List<Status> currentDatas) throws Exception {
                long maxId;
                maxId = getMaxId(skip, currentDatas);
                if (maxId == 0) {
                    return new ArrayList<>();
                } else {
                    return StatusService.getStatusDatas(maxId, limit);
                }
            }

            @Override
            public void onItemLongPressed(final Status item) {
                AVStatus innerStatus = item.getInnerStatus();
                AVUser source = innerStatus.getSource();
                if (source.getObjectId().equals(AVUser.getCurrentUser().getObjectId())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StatusListActivity.this);
                    builder.setMessage("要删除这条状态吗").setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new StatusNetAsyncTask(StatusListActivity.this) {
                                @Override
                                protected void doInBack() throws Exception {
                                    StatusService.deleteStatus(item);
                                }

                                @Override
                                protected void onPost(Exception e) {
                                    if (e != null) {
                                        StatusUtils.toast(StatusListActivity.this, e.getMessage());
                                    } else {
                                        statusList.onRefresh();
                                    }
                                }
                            }.execute();
                        }
                    }).setNegativeButton("取消", null);
                    builder.show();
                }
            }
        }, new StatusListAdapter(StatusListActivity.this));
    }

    public static long getMaxId(int skip, List<Status> currentDatas) {
        long maxId;
        if (skip == 0) {
            maxId = Long.MAX_VALUE;
        } else {
            AVStatus lastStatus = currentDatas.get(currentDatas.size() - 1).getInnerStatus();
            maxId = lastStatus.getMessageId() - 1;
        }
        return maxId;
    }

    @OnClick(R.id.share_take_photo)
    protected void goSend() {
        Intent intent = new Intent(StatusListActivity.this, StatusSendActivity.class);
        startActivityForResult(intent, SEND_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SEND_REQUEST) {
                statusList.onRefresh();
            }
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
