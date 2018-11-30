package com.buyint.mergerbot.UIs.verification.fragment

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.verification.activity.IntellectualRelationshipChartActivity
import com.buyint.mergerbot.dto.SourceTargetGetPeopleRelationshipModel

/**
 * Created by huheming on 2018/8/17
 */
class HistoryRelationAdapter(var context: Context, var list: ArrayList<SourceTargetGetPeopleRelationshipModel>) : RecyclerView.Adapter<HistoryRelationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val textView = holder.itemView.findViewById(android.R.id.text1) as TextView
            val model = list[position]
            textView.text = model.textTitle
            holder.itemView.setOnClickListener {
                val intent = Intent(context, IntellectualRelationshipChartActivity::class.java)
                intent.putExtra(context.getString(R.string.DATA), model.bean)
                context.startActivity(intent)
            }
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}