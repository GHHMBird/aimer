package com.buyint.mergerbot.UIs.matchRecord.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.buyint.mergerbot.base.BaseFragment

/**
 * Created by huheming on 2018/8/1
 */
class MatchRecordDetailPageAdapter(fm: FragmentManager, var list: ArrayList<BaseFragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

}