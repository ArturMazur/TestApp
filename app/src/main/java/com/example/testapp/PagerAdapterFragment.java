package com.example.testapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;

public class PagerAdapterFragment extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> fragmentTitleList;

    public PagerAdapterFragment(FragmentManager fm) {
        super(fm);

        fragments = new ArrayList<>();
        fragmentTitleList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitleList.add(title);
        notifyDataSetChanged();
    }

    public void removeFragment() {
        fragments.remove(fragments.size() - 1);
        fragmentTitleList.remove(fragmentTitleList.size() - 1);
        notifyDataSetChanged();
    }
}
