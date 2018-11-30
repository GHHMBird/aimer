package com.buyint.mergerbot.UIs.matchRecord.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.buyint.mergerbot.R
import com.buyint.mergerbot.dto.MatchRecordListModel
import kotlinx.android.synthetic.main.item_search_person.view.*

class SearchPeopleAdapter(var context: Context, var list: ArrayList<MatchRecordListModel>) : RecyclerView.Adapter<SearchPeopleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return SearchPeopleAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search_person, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setLists(list: ArrayList<MatchRecordListModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.itemView.apply {

            Glide.with(context).load(model.domain).asBitmap().centerCrop().error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(object : BitmapImageViewTarget(item_search_person_iv) {
                override fun setResource(resource: Bitmap) {
                    val rbd = RoundedBitmapDrawableFactory.create(resources, resource)
                    rbd.isCircular = true
                    item_search_person_iv.setImageDrawable(rbd)
                }
            })

            item_search_person_name.text = model.userName

            var companyName = model.companyName
            if (TextUtils.isEmpty(companyName)) {
                companyName = context.getString(R.string.undisclosed_company)
            }
            var positions = model.position
            if (TextUtils.isEmpty(positions)) {
                positions = context.getString(R.string.undisclosed_position)
            }
            item_search_person_company.text = "$companyName|$positions"

            item_search_person_add.setOnClickListener {

            }
        }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}
