package com.buyint.mergerbot.UIs.matchRecord.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.matchRecord.adapter.MatchRecordDetailPageAdapter
import com.buyint.mergerbot.UIs.matchRecord.fragment.RecyclerViewFragment
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.base.BaseFragment
import com.buyint.mergerbot.dto.MatchRecordListModel
import kotlinx.android.synthetic.main.activity_match_record_detail.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/7/25
 */
class MatchRecordDetailActivity : BaseActivity() {

    private lateinit var data: MatchRecordListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_match_record_detail)

        toolbar_white_top.visibility = View.GONE

        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_title.text = getString(R.string.match_record_detail)

        data = intent.getSerializableExtra(getString(R.string.DATA)) as MatchRecordListModel

        activity_match_record_detail_reliable.text = data.reliableRate!!.toInt().toString()
        activity_match_record_detail_name.text = data.userName
        activity_match_record_detail_company.text = data.companyName

        activity_match_record_detail_arrow.setOnClickListener {
            if (activity_match_record_detail_company_ll.visibility == View.GONE) {
                activity_match_record_detail_company_ll.visibility = View.VISIBLE
            } else {
                activity_match_record_detail_company_ll.visibility = View.GONE
            }
        }

        val list = ArrayList<BaseFragment>()
        list.add(RecyclerViewFragment(0, data.personId, object : RecyclerViewFragment.RecyclerViewInterface {
            override fun getNum(num: Int) {
                activity_match_record_detail_match_project_tv.text = num.toString()
            }
        }))
        list.add(RecyclerViewFragment(1, data.personId, object : RecyclerViewFragment.RecyclerViewInterface {
            override fun getNum(num: Int) {
                activity_match_record_detail_business_friend_tv.text = num.toString()
            }
        }))
        list.add(RecyclerViewFragment(2, data.personId, object : RecyclerViewFragment.RecyclerViewInterface {
            override fun getNum(num: Int) {
                activity_match_record_detail_client_tv.text = num.toString()
            }
        }))
        activity_match_record_detail_viewpager.adapter = MatchRecordDetailPageAdapter(supportFragmentManager, list)
        activity_match_record_detail_viewpager.offscreenPageLimit = 3
        val page = intent.getIntExtra(getString(R.string.TYPE), 0)
        activity_match_record_detail_viewpager.currentItem = page

        activity_match_record_detail_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> view1Click()
                    1 -> view2Click()
                    2 -> view3Click()
                }
            }
        })

        activity_match_record_detail_match_project_sbv.setOnClickListener {
            view1Click()
            activity_match_record_detail_viewpager.currentItem = 0
        }
        activity_match_record_detail_business_friend_sbv.setOnClickListener {
            view2Click()
            activity_match_record_detail_viewpager.currentItem = 1
        }
        activity_match_record_detail_client_sbv.setOnClickListener {
            view3Click()
            activity_match_record_detail_viewpager.currentItem = 2
        }

        when (page) {
            0 -> activity_match_record_detail_match_project_sbv.choose()
            1 -> activity_match_record_detail_business_friend_sbv.choose()
            2 -> activity_match_record_detail_client_sbv.choose()
        }
    }

    private fun view3Click() {
        activity_match_record_detail_match_project_sbv.deselectBackground()
        activity_match_record_detail_business_friend_sbv.deselectBackground()
        activity_match_record_detail_client_sbv.selectBackground()
    }

    private fun view2Click() {
        activity_match_record_detail_match_project_sbv.deselectBackground()
        activity_match_record_detail_business_friend_sbv.selectBackground()
        activity_match_record_detail_client_sbv.deselectBackground()
    }

    private fun view1Click() {
        activity_match_record_detail_match_project_sbv.selectBackground()
        activity_match_record_detail_business_friend_sbv.deselectBackground()
        activity_match_record_detail_client_sbv.deselectBackground()
    }
}