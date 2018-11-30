package com.buyint.mergerbot.UIs.user.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.activity.EducationSelectActivity

/**
 * Created by huheming on 2018/7/19
 */
class EducationSelectAdapter(var context: Context, var list: ArrayList<String>) : RecyclerView.Adapter<EducationSelectAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_education_select, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.tv.text = list[position]
        holder.itemView.setOnClickListener {
            val s = list[position]
            val intent = Intent()
            intent.putExtra(context.getString(R.string.DATA), s)
            (context as EducationSelectActivity).setResult(1001, intent)
            (context as EducationSelectActivity).finish()
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById(R.id.item_education_select_tv) as TextView
    }
}