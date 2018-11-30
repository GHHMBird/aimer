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
import com.buyint.mergerbot.UIs.user.activity.FocusAreaActivity
import com.buyint.mergerbot.dto.CodeNameBean
import com.buyint.mergerbot.view.FluidLayout
import com.buyint.mergerbot.dto.FluidLayoutBean

/**
 * Created by huheming on 2018/7/19
 */
class FocusAreaAdapter(var context: Context, var textList: ArrayList<CodeNameBean>, var chooseList: ArrayList<CodeNameBean>, var otherList: ArrayList<CodeNameBean>) : RecyclerView.Adapter<FocusAreaAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> 0
            textList.size + 1 -> 2
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            0 -> ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null, false), 0)
            1 -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_focus_area, null, false), 1)
            else -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_other_fluid, null, false), 2)
        }
    }

    override fun getItemCount(): Int {
        return textList.size + 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> holder.textview!!.text = context.getString(R.string.which_areas_of_the_industry_do_you_care_about)
            textList.size + 1 -> {
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
                        holder.fluid!!.visibility = View.VISIBLE
                    } else {
                        holder.iv!!.setImageResource(R.drawable.ring_gray)
                        otherList.clear()
                        holder.fluid!!.removeAllViews()
                        holder.fluid!!.visibility = View.GONE
                    }
                }
            }
            else -> {
                val bean = textList[position - 1]
                holder.tv!!.text = bean.name
                for (a in chooseList) {
                    if (bean.code == a.code && bean.name == a.name) {
                        holder.iv!!.setImageResource(R.drawable.ring_blue)
                        bean.isSelect = true
                    }
                }
                holder.itemView.setOnClickListener {
                    if (bean.isSelect) {
                        holder.iv!!.setImageResource(R.drawable.ring_gray)
                        var num = 0
                        for (a in chooseList.indices) {
                            if (bean.code == chooseList[a].code && bean.name == chooseList[a].name) {
                                num = a
                            }
                        }
                        chooseList.removeAt(num)
                    } else {
                        holder.iv!!.setImageResource(R.drawable.ring_blue)
                        chooseList.add(textList[position - 1])
                    }
                    bean.isSelect = !bean.isSelect
                }
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textview: TextView? = null
        var ll: LinearLayout? = null
        var fluid: FluidLayout? = null
        var tv: TextView? = null
        var iv: ImageView? = null

        constructor(itemView: View, itemType: Int) : this(itemView) {
            when (itemType) {
                0 -> textview = itemView.findViewById(android.R.id.text1) as TextView
                1 -> {
                    tv = itemView.findViewById(R.id.item_focus_area_tv) as TextView
                    iv = itemView.findViewById(R.id.item_focus_area_iv) as ImageView
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
            b.index = i.code
            b.text = i.name
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
                var num = 0
                for (b in otherList.indices) {
                    if (otherList[b].code == bean.index && otherList[b].name == bean.text) {
                        num = b
                    }
                }
                otherList.removeAt(num)
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
            intent.putExtra(context.getString(R.string.TYPE), 11)
            intent.putExtra(context.getString(R.string.NAME), "")
            (context as FocusAreaActivity).startActivityForResult(intent, 11)
        }

        val params = FluidLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 20, 30, 20)
        fluid.addView(tv, params)
    }

}