package com.example.chatapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.activities.ChatActivity
import com.example.chatapp.models.Friend
import com.google.android.material.imageview.ShapeableImageView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FriendsRecyclerAdapter : RecyclerView.Adapter<FriendsRecyclerAdapter.ViewHolder>() {

    var items: MutableList<Friend> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_friends, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val friend = items[position]
        holder.bind(friend)


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivFriend: ShapeableImageView = itemView.findViewById(R.id.ivFriend)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvLastMsg: TextView = itemView.findViewById(R.id.tvLastMsg)
        val tvHour: TextView = itemView.findViewById(R.id.tvHour)


        fun bind(friend: Friend) {

            tvName.text = friend.name
            tvLastMsg.text = friend.lastMsg
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            tvHour.text = sdf.format(Date(friend.timestamp))
            Glide.with(itemView.context).load(friend.image).placeholder(R.drawable.baseline_bug_report_24).into(ivFriend)
            itemView.setOnClickListener {

                Intent(itemView.context, ChatActivity::class.java).also {

                    it.putExtra("friend", friend.uuid)
                    itemView.context.startActivity(it)
                }
            }
        }

    }

}