package com.buyint.mergerbot.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by huheming on 2018/9/21
 */
class MyFragmentPagerAdapter(fm: FragmentManager, var list: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }


}