package com.chen.xyweather.ui;

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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.xyweather.R;
import com.chen.xyweather.base.BaseActivity;
import com.chen.xyweather.base.BaseFragment;
import com.chen.xyweather.model.UserHelper;
import com.chen.xyweather.model.UserInstance;
import com.chen.xyweather.utils.DebugLog;
import com.chen.xyweather.utils.DepthPageTransformer;
import com.chen.xyweather.utils.UiUtil;
import com.chen.xyweather.utils.UtilManger;
import com.chen.xyweather.view.CircleImageView;
import com.chen.xyweather.view.DynamicWeatherView;
import com.chen.xyweather.view.drawer.BaseDrawer;
import com.chen.xyweather.view.pager.MainViewPagerAdapter;
import com.chen.xyweather.view.pager.MyViewPager;
import com.chen.xyweather.view.pager.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    protected MainViewPagerAdapter viewPagerAdapter;

    protected List<BaseFragment> fragments;

    private long clickTime = 0;

    public static Typeface typeface;

    public static Typeface getTypeface(Context context) {
        return typeface;
    }

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
            //
            mViewPager.setPadding(0, UiUtil.getStatusBarHeight(), 0, 0);
        }

    }

    @Override
    protected void setupViews() {

        setupNavigation();
        initAlpha();

        setupViewPager();
        initData();
    }

    /**
     * 初始化用户数据
     */
    private void initData() {
        //用户有效
        if (new UserInstance().getInstance().getmUserStatus().equals(UserHelper.USER_STATUS.VALID)) {

        }
    }

    private boolean isUser() {
        return UserInstance.getInstance().getmUserStatus().equals(UserHelper.USER_STATUS.VALID);
    }
    /**
     * 设置侧滑栏
     */
    private void setupNavigation() {

        View headerView = mNavigation.getHeaderView(0);
        CircleImageView imageView = (CircleImageView) headerView.findViewById(R.id.nav_head_circle_view_header);
        TextView phone = (TextView) headerView.findViewById(R.id.nav_head_username);

        if (isUser()) {
            //
            DebugLog.e("avatar");
            if (UserInstance.getInstance().getmAvatar() != null) {
                imageView.setImageBitmap(UserInstance.getInstance().getmAvatar());
                DebugLog.e("avatar is not null");
            }

        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 17-4-28 添加账号信息
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "header image view", Toast.LENGTH_SHORT).show();
            }
        });
        //监听
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_menu_setting:
                        item.setChecked(true);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_add_city:
                        addCity("北京");
                        mViewPager.setCurrentItem(fragments.size() - 1, true);
                        break;
                    case R.id.nav_feedback:

                        item.setChecked(true);
                        break;
                    case R.id.nav_about:
                        item.setChecked(true);
                        break;
                    case R.id.nav_menu_care:
                        item.setChecked(true);
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

//        fragments.add(new SettingFragment());
        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        mViewPager.setCurrentItem(0, false);
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                mDynamicWeatherView.setDrawerType(((MainViewPagerAdapter)mViewPager.getAdapter()).
//                        getItem(position).getDrawerType());
//
//            }
//        });
//
//
//        new Thread() {
//
//
//            @Override
//            public void run() {
//                super.run();
//                mDynamicWeatherView.setDrawerType(((MainViewPagerAdapter) mViewPager.getAdapter()).getItem(
//                        mViewPager.getCurrentItem()).getDrawerType());
//                DebugLog.e("thread run ");
//
//            }
//        }.start();
////        updateCurDrawerType();
    }

    public void addCity(String city) {
        fragments.add(WeatherFragment.newInstance(city));
        viewPagerAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(viewPagerAdapter);
    }

    /**
     * 更新天气类型
     */
    public void updateCurDrawerType() {

//                DebugLog.e("test drawer chen-->");
//                ((MainViewPagerAdapter)mViewPager.getAdapter()).getItem(mViewPager.getCurrentItem()).getDrawerType();
//                int position = mViewPager.getCurrentItem();
//                DebugLog.e("test drawer chen--> " + position);
//                BaseFragment fragment = viewPagerAdapter.getItem(position);
//                BaseDrawer.Type type = fragment.getDrawerType();

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
//        Window window = getWindow();
//        WindowManager.LayoutParams windowLayoutParams = window.getAttributes();
//        windowLayoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        window.setAttributes(windowLayoutParams);
//
//        View hackyStatusView = findViewById(R.id.fake_status_bar);
//        if (null != hackyStatusView) {
//            RelativeLayout.LayoutParams statusViewLayoutParams =
//                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                            UiUtil.getStatusBarHeight());
//            hackyStatusView.setLayoutParams(statusViewLayoutParams);
//            TypedValue typedValue = new TypedValue();
//            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
//            hackyStatusView.setBackgroundColor(typedValue.data);
//        }
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
            Toast.makeText(this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }
}
