package com.example.cbutcherproject3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbutcherproject3.Item.All.itemList
import com.example.cbutcherproject3.Item.All.items


class MainActivity : AppCompatActivity(), Adapter.OnItemClickListener {

    private val list = getList()
    //private var mutableList =  Item.makeList(list)
    private val adapter = Adapter(list, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        Log.i("CS3680", "oncreate in main $list")
    }


    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = list[position]
        val intent = Intent(this, ItemActivity::class.java)
        // pass recyclerview position for use of removeAt & other methods
        intent.putExtra("position", position)
        // pass the name of individual item clicked
        intent.putExtra("itemtoremove", clickedItem.item)
        startActivity(intent)
        adapter.notifyItemChanged(position)
    }

    // navigate to add item activity
    fun insertItem(view: View) {
        val intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }

    // onresume method reload list data after returning from other view
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
    // return the list for recyclerview
    private fun getList(): List<Item> {
        return Item.All.items
    }
}