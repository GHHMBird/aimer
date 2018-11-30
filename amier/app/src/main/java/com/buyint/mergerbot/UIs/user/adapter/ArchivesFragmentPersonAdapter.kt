package com.buyint.mergerbot.UIs.user.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.buyint.mergerbot.R

/**
 * Created by huheming on 2018/7/12
 */
class ArchivesFragmentPersonAdapter(var context: Context, var list: ArrayList<String>) : RecyclerView.Adapter<ArchivesFragmentPersonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_archives_fragment_person, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addPeople(name: String) {
        list.add(name)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.name.text = list[position]
        holder!!.delete.setOnClickListener {
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.item_archives_fragment_people_name) as TextView
        val delete = view.findViewById(R.id.item_archives_fragment_people_delete) as ImageView
    }
}