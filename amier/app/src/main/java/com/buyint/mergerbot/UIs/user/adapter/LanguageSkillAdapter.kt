package com.buyint.mergerbot.UIs.user.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity
import com.buyint.mergerbot.UIs.user.activity.LanguageSkillActivity
import com.buyint.mergerbot.view.FluidLayout
import com.buyint.mergerbot.dto.FluidLayoutBean

/**
 * Created by huheming on 2018/7/18
 */
class LanguageSkillAdapter(var context: Context, var picList: ArrayList<Int>, var enumList: ArrayList<String>, private var textList: ArrayList<Int>, var chooseList: ArrayList<String>, var otherList: ArrayList<String>) : RecyclerView.Adapter<LanguageSkillAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> 0
            8 -> 2
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            0 -> ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null, false), 0)
            1 -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_language_skill, null, false), 1)
            else -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_other_fluid, null, false), 2)
        }
    }

    override fun getItemCount(): Int {
        return 9
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.textView!!.text = context.getString(R.string.multiple_choice)
        } else if (position == 8) {
            if (otherList.size > 0) {
                holder.iv!!.setImageResource(R.drawable.ring_blue)
                initFluidLayout(holder.fluid!!, initList())
            } else {
                holder.iv!!.setImageResource(R.drawable.ring_gray)
            }
            holder.ll!!.setOnClickListener {
                if (holder.fluid!!.childCount == 0) {
                    holder.iv!!.setImageResource(R.drawable.ring_blue)
                    initFluidLayout(holder.fluid!!, initList())
                } else {
                    holder.iv!!.setImageResource(R.drawable.ring_gray)
                    otherList.clear()
                    holder.fluid!!.removeAllViews()
                }
            }
        } else {
            holder.country!!.setImageResource(picList[position - 1])
            holder.tv!!.text = context.getString(textList[position - 1])
            if (chooseList.contains(enumList[position - 1])) {
                holder.iv!!.setImageResource(R.drawable.ring_blue)
            } else {
                holder.iv!!.setImageResource(R.drawable.ring_gray)
            }
            holder.itemView.setOnClickListener {
                if (chooseList.contains(enumList[position - 1])) {
                    chooseList.remove(enumList[position - 1])
                    holder.iv!!.setImageResource(R.drawable.ring_gray)
                } else {
                    chooseList.add(enumList[position - 1])
                    holder.iv!!.setImageResource(R.drawable.ring_blue)
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var country: ImageView? = null
        var tv: TextView? = null
        var iv: ImageView? = null
        var textView: TextView? = null
        var ll: LinearLayout? = null
        var fluid: FluidLayout? = null

        constructor(itemView: View, type: Int) : this(itemView) {
            when (type) {
                0 -> textView = itemView.findViewById(android.R.id.text1) as TextView
                1 -> {
                    country = itemView.findViewById(R.id.item_language_skill_country) as ImageView
                    tv = itemView.findViewById(R.id.item_language_skill_tv) as TextView
                    iv = itemView.findViewById(R.id.item_language_skill_iv) as ImageView
                }
                else -> {
                    ll = itemView.findViewById(R.id.item_other_fluid_ll) as LinearLayout
                    iv = itemView.findViewById(R.id.item_other_fluid_iv) as ImageView
                    fluid = itemView.findViewById(R.id.item_other_fluid_fluid) as FluidLayout
                }
            }
        }
    }


    private fun initList(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in otherList!!) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        return list
    }

    private fun initFluidLayout(fluid: FluidLayout, list: ArrayList<FluidLayoutBean>) {
        fluid.removeAllViews()
        fluid.setGravity(Gravity.TOP)
        for (i in list.indices) {

            val bean = list[i]

            val tv = TextView(context)
            tv.setPadding(25, 0, 25, 0)
            tv.text = bean.text
            tv.gravity = Gravity.CENTER
            tv.textSize = 14f
            tv.isClickable = true

            tv.setTextColor(ContextCompat.getColor(context, R.color.color_2d02d3))
            tv.setBackgroundResource(R.mipmap.bf_match_delete)

            tv.setOnClickListener {
                otherList.remove(bean.text)
                initFluidLayout(fluid, initList())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            fluid.addView(tv, params)
        }

        val tv = TextView(context)
        tv.setPadding(50, 25, 50, 25)
        tv.text = "+"
        tv.gravity = Gravity.CENTER
        tv.textSize = 14f
        tv.isClickable = true

        tv.setTextColor(ContextCompat.getColor(context, R.color.color_959dce))
        tv.setBackgroundResource(R.drawable.tx_bg_blue2)

        tv.setOnClickListener {
            val intent = Intent(context, QuickMatchActivity::class.java)
            intent.putExtra(context.getString(R.string.NAME), context.getString(R.string.please_add_your_language_skill))
            (context as LanguageSkillActivity).startActivityForResult(intent, 1100)
        }

        val params = FluidLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 20, 30, 20)
        fluid.addView(tv, params)
    }

}