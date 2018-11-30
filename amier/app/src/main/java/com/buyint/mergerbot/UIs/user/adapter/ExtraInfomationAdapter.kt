package com.buyint.mergerbot.UIs.user.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.activity.EducationExperienceWriteActivity
import com.buyint.mergerbot.dto.AddEducationExperienceRequest
import java.text.SimpleDateFormat

/**
 * Created by huheming on 2018/7/19
 */
class ExtraInfomationAdapter(var context: Context, var list: ArrayList<AddEducationExperienceRequest>, var isClick: Boolean) : RecyclerView.Adapter<ExtraInfomationAdapter.ViewHolder>() {

    var sdf = SimpleDateFormat("yyyy年MM月")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_extra_information, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        val sb = StringBuffer()
        if (TextUtils.isEmpty(model.schoolName)) {
            sb.append(context.getString(R.string.undisclosed_division))
        } else {
            sb.append(model.schoolName)
        }
        sb.append("|")
        if (model.professional != null) {
            for (a in model.professional!!) {
                sb.append(a).append("|")
            }
        }
        sb.append(model.educationBackground)
        holder!!.title.text = sb.toString()

        holder.time.text = sdf.format(model.startTime) + "-" + sdf.format(model.endTime)

        if (!isClick) {
            holder.clickView.visibility = View.GONE
        }

        if (isClick) {
            holder.itemView.setOnClickListener {
                val intent = Intent(context, EducationExperienceWriteActivity::class.java)
                intent.putExtra(context.getString(R.string.DATA), model)
                context.startActivity(intent)
            }
        }
    }

    fun setLists(list: ArrayList<AddEducationExperienceRequest>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.item_extra_information_title) as TextView
        val time = itemView.findViewById(R.id.item_extra_information_time) as TextView
        val clickView = itemView.findViewById(R.id.item_extra_information_isclick) as ImageView
    }
}