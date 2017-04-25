package com.chen.xyweather.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.utils.DataCleanManager;


import com.chen.xyweather.utils.MapUtil;
import com.chen.xyweather.view.drawer.BaseDrawer;

import java.io.File;
import java.util.Properties;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.lang.System.getProperties;

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

    @Bind(R.id.s_cache)
    protected TextView tv_cache;


    @Bind(R.id.s_notice)
    protected RelativeLayout tv_notice;

    @OnClick(R.id.s_notice)
    protected void notice() {
        Toast.makeText(getActivity(), MapUtil.getLocation(getActivity()),Toast.LENGTH_LONG).show();
    }

    @Bind(R.id.s_changeskin)
    protected RelativeLayout tv_changeskin;

    @OnClick(R.id.s_changeskin)
    protected void changeskin() {

    }

    @Bind(R.id.s_cleardata)
    protected RelativeLayout tv_cleardata;

    @OnClick(R.id.s_cleardata)
    protected void cleardata() {
        DataCleanManager.clearAllCache(getActivity());
        tv_cache.setText("0KB");
    }


    @Bind(R.id.s_about)
    protected RelativeLayout tv_about;

    @OnClick(R.id.s_about)
    protected void about() {

    }

    @Bind(R.id.s_feedback)
    protected RelativeLayout tv_feedback;

    @OnClick(R.id.s_feedback)
    protected void feedback() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, null);

        ButterKnife.bind(this, view);
        try {
            tv_cache.setText(DataCleanManager.getTotalCacheSize(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

//    //清理缓存
//    private void clearAppCache() {
//        myclearaAppCache();
//    }
//
//    //计算缓存大小
//    private void calculateCacheSize() {
//        long fileSize = 0;
//        String cacheSize = "0KB";
//        File filesDir = getActivity().getFilesDir();
//        File cacheDir = getActivity().getCacheDir();
//
//        fileSize += FileUtil.getDirSize(filesDir);
//        fileSize += FileUtil.getDirSize(cacheDir);
//        // 2.2版本才有将应用缓存转移到sd卡的功能
//        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
//            File externalCacheDir = MethodsCompat
//                    .getExternalCacheDir(getActivity());
//            fileSize += FileUtil.getDirSize(externalCacheDir);
//            fileSize += FileUtil.getDirSize(new File(
//                    org.kymjs.kjframe.utils.FileUtils.getSDCardPath()
//                            + File.separator + "KJLibrary/cache"));
//        }
//        if (fileSize > 0)
//            cacheSize = FileUtil.formatFileSize(fileSize);
//        tv_cache.setText(cacheSize);
//    }
//
//    public static boolean isMethodsCompat(int VersionCode) {
//        int currentVersion = android.os.Build.VERSION.SDK_INT;
//        return currentVersion >= VersionCode;
//    }
//
//    /**
//     * 清除app缓存
//     */
//    public void myclearaAppCache() {
//        deleteDir(getActivity().getCacheDir());
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            deleteDir(getActivity().getExternalCacheDir());
//        }
//    }
//    private static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//        return dir.delete();
//    }

}
