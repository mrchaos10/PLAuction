package com.example.plauction.Adapters;

import android.os.Parcelable;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;
    FragmentManager manager;

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {}

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public PageAdapter(FragmentManager manager) {
        super(manager);
        this.manager = manager;

    }

    @Override
    public Fragment getItem(int position) {
        if(position == mFragmentList.size()){

        }
        return mFragmentList.get(position);
    }



    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title, int position) {
        mFragmentList.add(position, fragment);
        mFragmentTitleList.add(position, title);
    }

    public void removeFragment(Fragment fragment, int position) {
        mFragmentList.remove(position);
        mFragmentTitleList.remove(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
