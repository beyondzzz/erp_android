package com.jl.jlapp.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	ArrayList<Fragment> listdatas;
	public MyFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> listdatas) {	
		super(fm);
		this.listdatas=listdatas;
	}

	@Override
	public Fragment getItem(int arg0) {
		return listdatas.get(arg0);
	}

	@Override
	public int getCount() {
		return listdatas.size();
	}

}
