package com.buyint.mergerbot.UIs.user.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.activity.ProjectPerformanceWriteActivity
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListModel
import java.text.SimpleDateFormat

/**
 * Created by huheming on 2018/7/10
 */
class ProjectPerformanceAdapter(var context: Context, val list: ArrayList<GetUserProjectPerformanceListModel>, var canClick: Boolean) : RecyclerView.Adapter<ProjectPerformanceAdapter.PerformanceAchievementViewHolder>() {
    override fun onBindViewHolder(holder: PerformanceAchievementViewHolder, position: Int) {
        val model = list!![position]
        holder!!.company.text = model.companyName
        val sdf = SimpleDateFormat("yyyy")
        holder.time.text = sdf.format(model.startTime) + "-" + sdf.format(model.endTime)
        when (position) {
            0 -> {
                holder.topLine.visibility = View.INVISIBLE
            }
        }
        holder.desc.text = "职业业绩\n" + model.projectDescription
        if (canClick) {
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ProjectPerformanceWriteActivity::class.java)
                intent.putExtra(context.getString(R.string.DATA), model.id)
                context.startActivity(intent)
            }
        } else {
            holder.clickView.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformanceAchievementViewHolder {
        return PerformanceAchievementViewHolder(View.inflate(context, R.layout.item_performance_achievement, null))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class PerformanceAchievementViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val company = itemView!!.findViewById(R.id.item_performance_achievement_company) as TextView
        val time = itemView!!.findViewById(R.id.item_performance_achievement_time) as TextView
        val desc = itemView!!.findViewById(R.id.item_performance_achievement_desc) as TextView
        val topLine:View = itemView!!.findViewById(R.id.item_performance_achievement_top_line)!!
        val clickView : ImageView = itemView!!.findViewById(R.id.item_performance_achievement_click_iv)!!
    }


}