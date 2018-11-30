package com.buyint.mergerbot.UIs.loginAndRegister.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

public class RegistrationActivityViewPager extends FragmentPagerAdapter {


    private ArrayList<Fragment> list = new ArrayList<>();

    public RegistrationActivityViewPager(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    public void addAdapter(Fragment fragment) {
        list.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public boolean contains(Fragment f) {
        return list.contains(f);
    }
}