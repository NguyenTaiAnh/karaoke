package com.example.karaoke_app.Adaptertheloai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.karaoke_app.R
import kotlinx.android.synthetic.main.item_baihat.view.*


class Adptertbaihat(private val list : List<baihat>) : RecyclerView.Adapter<Adptertbaihat.ViewHolder>() {

     class  ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tenhaihat : TextView = itemview.tvtenbh
        val id : TextView = itemview.tvcasi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_baihat,parent, false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currenItem = list[position]
        holder.tenhaihat.text = currenItem.name
        holder.id.text = currenItem.id
    }

    override fun getItemCount() = list.size
}