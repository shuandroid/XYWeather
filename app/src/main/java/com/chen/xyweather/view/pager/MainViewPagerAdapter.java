package com.chen.xyweather.view.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.chen.xyweather.base.BaseFragment;

import java.util.List;

/**
 * Created by chen on 17-4-5.
 *  adapter
 */
public class MainViewPagerAdapter  extends FragmentStatePagerAdapter{

    private List<BaseFragment> mFragments;
    private int childCount;

    public MainViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }


    @Override
    public BaseFragment getItem(int position) {
        //// TODO: 17-4-10 做什么的？
        mFragments.get(position).setRetainInstance(true);
        return mFragments.get(position);
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(((Fragment)object).getView());
        super.destroyItem(container, position, object);

    }

    @Override
    public void notifyDataSetChanged() {
        childCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (childCount > 0) {
            childCount--;
            return POSITION_NONE;
        }

        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}
