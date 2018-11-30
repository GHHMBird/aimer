package com.buyint.mergerbot.UIs.user.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.activity.BusinessActivityWriteActivity
import com.buyint.mergerbot.dto.GetUserBusinessActivityListModel
import java.text.SimpleDateFormat

/**
 * Created by huheming on 2018/7/18
 */
class BusinessActivityAdapter(var context: Context, var list: ArrayList<GetUserBusinessActivityListModel>?, var canClick: Boolean) : RecyclerView.Adapter<BusinessActivityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_business_activity, null, false))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list!![position]
        val sdf = SimpleDateFormat("yyyy")
        holder.title.text = model.activityName
        holder.time.text = sdf.format(model.startTime) + "-" + sdf.format(model.endTime)
        holder.desc.text = model.activityDescription
        if (model.parties != null && model.parties!!.size > 0) {
            val sb = StringBuffer()
            for (text in model.parties!!) {
                sb.append("$text ")
            }
            holder.people.text = "参与人员：$" + sb.toString()
        }
        if (model.activityType != null && model.activityType!!.size > 0) {
            val sb = StringBuffer()
            for (text in model.activityType!!) {
                sb.append(text + " ")
            }
            holder.type.text = "类别：" + sb.toString()
        }

        if (canClick) {
            holder.itemView.setOnClickListener {
                val intent = Intent(context, BusinessActivityWriteActivity::class.java)
                intent.putExtra(context.getString(R.string.DATA), model.id)
                context.startActivity(intent)
            }
        } else {
            holder.clickView.visibility = View.GONE
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.item_business_activity_title) as TextView
        val time = itemView.findViewById(R.id.item_business_activity_time) as TextView
        val desc = itemView.findViewById(R.id.item_business_activity_desc) as TextView
        val people = itemView.findViewById(R.id.item_business_activity_people) as TextView
        val type = itemView.findViewById(R.id.item_business_activity_type) as TextView
        val clickView: ImageView = itemView.findViewById(R.id.item_business_activity_click_iv)
    }
}