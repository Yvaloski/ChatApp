package com.example.chatapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.activities.ChatActivity
import com.example.chatapp.models.User

class UserRecyclerAdapter : RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>(), Filterable {


    var items: MutableList<User> = mutableListOf()
        set(value) {
            field = value
            userFilteredList=value
            notifyDataSetChanged()
        }
    private var userFilteredList: MutableList<User> = mutableListOf()

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {

                    userFilteredList = items
                }else{
                    val resultList = items.filter { it.pseudo.lowercase().contains(charSearch) }
                    userFilteredList = resultList as MutableList<User>
                }

                val filterResult = FilterResults()
                filterResult.values = userFilteredList
                return  filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                userFilteredList = results?.values as MutableList<User>
                notifyDataSetChanged()

            }

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = userFilteredList[position]
        holder.bind(user)


    }

    override fun getItemCount() = userFilteredList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvShortName: TextView = itemView.findViewById(R.id.tv_shotName)
        val tvNames: TextView = itemView.findViewById(R.id.tvNames)

        fun bind(user: User) {

            tvShortName.text = user.pseudo[0].toString()
            tvNames.text = user.pseudo

            itemView.setOnClickListener{

                Intent(itemView.context,ChatActivity::class.java).also {
                    it.putExtra("friend",user.uuid)
                    itemView.context.startActivity(it)
                }
            }
        }


    }


}