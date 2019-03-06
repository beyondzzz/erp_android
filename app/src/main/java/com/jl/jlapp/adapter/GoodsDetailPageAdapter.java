package com.jl.jlapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 柳亚婷 on 2018/3/13 0013.
 */

public class GoodsDetailPageAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;

    public GoodsDetailPageAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        //将fm_list中添加到了adapter中

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
