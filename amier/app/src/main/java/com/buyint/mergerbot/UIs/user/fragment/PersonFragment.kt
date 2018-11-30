package com.buyint.mergerbot.UIs.user.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseFragment

/**
 * Created by huheming on 2018/7/11
 */
class PersonFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            see_me_rl1 -> {
                see_me_iv1!!.setImageResource(R.drawable.ring_blue)
                see_me_iv2!!.setImageResource(R.drawable.ring_gray)
                see_me_iv3!!.setImageResource(R.drawable.ring_gray)
                see_me_iv4!!.setImageResource(R.drawable.ring_gray)
            }
            see_me_rl2 -> {
                see_me_iv1!!.setImageResource(R.drawable.ring_gray)
                see_me_iv2!!.setImageResource(R.drawable.ring_blue)
                see_me_iv3!!.setImageResource(R.drawable.ring_gray)
                see_me_iv4!!.setImageResource(R.drawable.ring_gray)
            }
            see_me_rl3 -> {
                see_me_iv1!!.setImageResource(R.drawable.ring_gray)
                see_me_iv2!!.setImageResource(R.drawable.ring_gray)
                see_me_iv3!!.setImageResource(R.drawable.ring_blue)
                see_me_iv4!!.setImageResource(R.drawable.ring_gray)
            }
            see_me_rl4 -> {
                see_me_iv1!!.setImageResource(R.drawable.ring_gray)
                see_me_iv2!!.setImageResource(R.drawable.ring_gray)
                see_me_iv3!!.setImageResource(R.drawable.ring_gray)
                see_me_iv4!!.setImageResource(R.drawable.ring_blue)
            }
            match_me_rl1 -> {
                match_me_iv1!!.setImageResource(R.drawable.ring_blue)
                match_me_iv2!!.setImageResource(R.drawable.ring_gray)
            }
            match_me_rl2 -> {
                match_me_iv1!!.setImageResource(R.drawable.ring_gray)
                match_me_iv2!!.setImageResource(R.drawable.ring_blue)
            }
        }
    }

    private var see_me_rl1: RelativeLayout? = null
    private var see_me_rl2: RelativeLayout? = null
    private var see_me_rl3: RelativeLayout? = null
    private var see_me_rl4: RelativeLayout? = null
    private var match_me_rl1: RelativeLayout? = null
    private var match_me_rl2: RelativeLayout? = null

    private var see_me_iv1: ImageView? = null
    private var see_me_iv2: ImageView? = null
    private var see_me_iv3: ImageView? = null
    private var see_me_iv4: ImageView? = null
    private var match_me_iv1: ImageView? = null
    private var match_me_iv2: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_person, null, false)

        see_me_rl1 = inflate.findViewById(R.id.fragment_person_who_can_see_me_rl1) as RelativeLayout
        see_me_rl2 = inflate.findViewById(R.id.fragment_person_who_can_see_me_rl2) as RelativeLayout
        see_me_rl3 = inflate.findViewById(R.id.fragment_person_who_can_see_me_rl3) as RelativeLayout
        see_me_rl4 = inflate.findViewById(R.id.fragment_person_who_can_see_me_rl4) as RelativeLayout
        match_me_rl1 = inflate.findViewById(R.id.fragment_person_match_me_rl1) as RelativeLayout
        match_me_rl2 = inflate.findViewById(R.id.fragment_person_match_me_rl2) as RelativeLayout

        see_me_iv1 = inflate.findViewById(R.id.fragment_person_who_can_see_me_iv1) as ImageView
        see_me_iv2 = inflate.findViewById(R.id.fragment_person_who_can_see_me_iv2) as ImageView
        see_me_iv3 = inflate.findViewById(R.id.fragment_person_who_can_see_me_iv3) as ImageView
        see_me_iv4 = inflate.findViewById(R.id.fragment_person_who_can_see_me_iv4) as ImageView
        match_me_iv1 = inflate.findViewById(R.id.fragment_person_match_me_iv1) as ImageView
        match_me_iv2 = inflate.findViewById(R.id.fragment_person_match_me_iv2) as ImageView

        see_me_rl1!!.setOnClickListener(this)
        see_me_rl2!!.setOnClickListener(this)
        see_me_rl3!!.setOnClickListener(this)
        see_me_rl4!!.setOnClickListener(this)
        match_me_rl1!!.setOnClickListener(this)
        match_me_rl2!!.setOnClickListener(this)

        return inflate
    }

}