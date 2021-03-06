package com.chen.xyweather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Build;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.model.UserHelper;
import com.chen.xyweather.model.UserInstance;
import com.chen.xyweather.model.UserModel;
import com.chen.xyweather.ui.fragment.AddWeatherFragment;
import com.chen.xyweather.ui.fragment.WeatherFragment;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.DepthPageTransformer;
import com.chen.xyweather.utils.UiUtil;
import com.chen.xyweather.utils.Utils;
import com.chen.xyweather.view.CircleImageView;
import com.chen.xyweather.view.DynamicWeatherView;
import com.chen.xyweather.view.pager.MainViewPagerAdapter;
import com.chen.xyweather.view.pager.MyViewPager;
import com.squareup.picasso.Picasso;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.leancloud.chatkit.LCChatKit;

public class MainActivity extends BaseActivity {


    protected MainViewPagerAdapter viewPagerAdapter;

    protected List<BaseFragment> fragments;

    private CircleImageView mHeadImage;
    private View mHeadView;
    private TextView mHeadPhone;

    private long clickTime = 0;

    public static Typeface typeface;

    public static Typeface getTypeface(Context context) {
        return typeface;
    }

    //城市选择
    private static final int REQUEST_CODE_PICK_CITY = 0;
    private String cityChoose;
    //定位相关
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private String locationResult;

    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;

    @Bind(R.id.main_weather_view)
    protected DynamicWeatherView mDynamicWeatherView;

    @Bind(R.id.main_viewpager)
    protected MyViewPager mViewPager;

    @Bind(R.id.navigation_view)
    protected NavigationView mNavigation;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViews() {

        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionbar() {

        if (Build.VERSION.SDK_INT >= 19) {
            mViewPager.setPadding(0, UiUtil.getStatusBarHeight(), 0, 0);
        }
    }

    @Override
    protected void setupViews() {

        setupNavigation();
        initAlpha();

//        if (!DBManger.isMapGet(this)) {
//            initLocation();
//            DebugLog.e("第一次定位");
//        }
        initLocation();

        setupViewPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        imLogin();
        updateHead();
    }

    /**
     * 更新侧滑栏头部
     */
    private void updateHead() {

        if (isUser()) {
            //
            DebugLog.e("avatar");
            if (UserInstance.getInstance().getmAvatar() != null) {
                Picasso.with(this).load(UserInstance.getInstance().
                        getmUser().getAvatarUrl()).into(mHeadImage);
//                mHeadImage.setImageBitmap(UserInstance.getInstance().getmAvatar());
                DebugLog.e("avatar is not null");
            }
            mHeadPhone.setText(UserInstance.getInstance().getmPhone());
        } else {
            mHeadPhone.setText("未登录");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                //cityName为选择城市获得的信息
                cityChoose = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                Toast.makeText(MainActivity.this, cityChoose, Toast.LENGTH_SHORT).show();
                addCity(cityChoose);
            }
        }
    }

    /**
     * 初始化用户数据
     */
    private void initData() {
    }

    private boolean isUser() {
        boolean valid = UserInstance.getInstance().getmUserStatus().equals(UserHelper.USER_STATUS.VALID);
        DebugLog.e("isUser " + valid);
        return valid;
    }

    /**
     * 设置侧滑栏
     */
    private void setupNavigation() {

        mHeadView = mNavigation.getHeaderView(0);
        mHeadImage = (CircleImageView) mHeadView.findViewById(R.id.nav_head_circle_view_header);
        mHeadPhone = (TextView) mHeadView.findViewById(R.id.nav_head_username);
        mHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUser()) {
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


        //监听
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                    switch (item.getItemId()) {
                    case R.id.nav_menu_setting:
                        item.setChecked(true);
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    case R.id.nav_add_city:
                        startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
                                REQUEST_CODE_PICK_CITY);
                        break;
                    case R.id.nav_feedback:
                        item.setChecked(true);
                        FeedbackAgent agent = new FeedbackAgent(MainActivity.this);
                        agent.startDefaultThreadActivity();
                        break;
                    case R.id.nav_share:
                        item.setChecked(true);
                        if (isUser()) {
                            startActivity(new Intent(MainActivity.this, StatusListActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "尚未登录", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        break;
                    case R.id.nav_about:
                        item.setChecked(true);
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    case R.id.nav_menu_care:
                        item.setChecked(true);
                        if (isUser()) {
                            startActivity(new Intent(MainActivity.this, CareActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "尚未登录", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void setupViewPager() {
        fragments = new ArrayList<>();
        fragments.add(WeatherFragment.newInstance(null));

        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        mViewPager.setCurrentItem(0, false);
    }

    public void addCity(String city) {
        fragments.add(AddWeatherFragment.newInstance(city));
        viewPagerAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setCurrentItem(fragments.size() - 1, false);
    }

    /**
     * 更新天气类型
     */
    public void updateCurDrawerType() {

        DebugLog.e("test drawer chen type--> ");
        mDynamicWeatherView.setDrawerType(((MainViewPagerAdapter) mViewPager.
                getAdapter()).getItem(mViewPager.getCurrentItem()).getDrawerType());
    }

    /**
     * 初始化alpha
     */
    private void initAlpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(260);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                getWindow().setBackgroundDrawable(new ColorDrawable(Color.alpha(R.color.colorPrimary)));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mViewPager.setAnimation(alphaAnimation);
    }


    @Override
    protected void tintStatusBarApi21() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        mDrawerLayout.setStatusBarBackgroundColor(typedValue.data);
    }

    @Override
    protected void tintStatusBarApi19() {
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (System.currentTimeMillis() - clickTime > 2000) {
            Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }


    /**
     * 定位相关 初始化
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        // 启动定位
        locationClient.startLocation();
    }

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                locationResult = Utils.getLocationStr(loc);
                DebugLog.e("get:" + locationResult);
//                if (locationResult != null) {
//                    DBManger.insertLocalCity(MainActivity.this, locationResult);
//                }
                stopLocation();
            } else {
                locationResult = "武汉";
            }
        }
    };

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }


    private void imLogin() {
        if (isUser()) {
            LCChatKit.getInstance().open(UserModel.getCurrentUserId(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                }
            });
        }

    }
}
