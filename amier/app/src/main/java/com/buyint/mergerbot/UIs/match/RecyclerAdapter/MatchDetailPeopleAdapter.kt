package com.buyint.mergerbot.UIs.match.RecyclerAdapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.buyint.mergerbot.R
import com.buyint.mergerbot.dto.IndustrySuperiorModel

/**
 * Created by huheming on 2018/6/15
 */
class MatchDetailPeopleAdapter(val context: Context, var list: ArrayList<IndustrySuperiorModel>) : RecyclerView.Adapter<MatchDetailPeopleAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_people, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        Glide.with(context).load(model.avatarUrl).placeholder(R.mipmap.default_user).error(R.mipmap.default_user).into(holder.ivIcon)
        holder.tvName.text = model.name
        holder.tvCompany.text = model.companyName
        holder.tvDesc.text = model.discription
        if (context.getString(R.string.lawyer) == model.role) {
            holder.tvIndustry.text = model.role
            holder.tvIndustry.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue))
        } else if (context.getString(R.string.investor) == model.role) {
            holder.tvIndustry.text = model.role
            holder.tvIndustry.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
        }
    }


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon = view.findViewById(R.id.item_people_iv) as ImageView
        val tvName = view.findViewById(R.id.item_people_name) as TextView
        val tvCompany = view.findViewById(R.id.item_people_company) as TextView
        val tvIndustry = view.findViewById(R.id.item_people_industry) as TextView
        val tvDesc = view.findViewById(R.id.item_people_desc) as TextView
    }
}