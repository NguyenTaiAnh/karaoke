package com.example.karaoke_app.Adaptertheloai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.karaoke_app.R
import com.example.karaoke_app.entity.User
import com.example.karaoke_app.UserViewHolder
import kotlinx.android.synthetic.main.item_baihat.view.*


class Adptertbaihat(val music : List<User>) : RecyclerView.Adapter<UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_baihat,parent, false)
        return UserViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = music[position]
        holder.bind(user)
    }

    override fun getItemCount() = music.size
}