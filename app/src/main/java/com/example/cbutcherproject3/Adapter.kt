package com.example.cbutcherproject3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter(private val list: List<Item>,
              private val listener: OnItemClickListener
) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,
        parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.item.text = currentItem.item

    }
    override fun getItemCount() = list.size

    fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        //val dbHelper = GroceryDatabaseHelper(this)
        // an item was selected. retrieve the selected item with pos
        val item = parent.selectedItem.toString()
        Log.i("CS3680", "$item")

       // dbHelper.queryRecords(item)
        Item.All.filterItems(item)
        notifyDataSetChanged()

    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val item: TextView = itemView.findViewById(R.id.item)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}