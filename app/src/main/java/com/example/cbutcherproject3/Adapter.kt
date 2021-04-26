package com.example.cbutcherproject3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter(private val list: List<IndividualList>,
              private val listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_of_lists_item,
        parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = list[position]
        holder.list.text = currentList.name
    }
    override fun getItemCount() = list.size

    inner class ViewHolder(listView: View) : RecyclerView.ViewHolder(listView),
    View.OnClickListener{
        val list: TextView = listView.findViewById(R.id.listItem)
        val deleteBtn: Button = listView.findViewById(R.id.deleteListButton)


        init {
            listView.setOnClickListener(this)
            deleteBtn.setOnClickListener{onDeleteButtonClick(listView)}

        }


        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
        fun onDeleteButtonClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION ) {
                listener.onDeleteButtonClick(position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteButtonClick(position: Int)

    }
}