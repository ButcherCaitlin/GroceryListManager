package com.example.cbutcherproject3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity(), Adapter.OnItemClickListener {


    companion object { var lists = mutableListOf<IndividualList>() }
    //private val list = dbHelper.getGroceries()
    val listdbHelper = ListManagerDatabaseHelper(this)
    val dbHelper = GroceryDatabaseHelper(this)
    //private var mutableList =  Item.makeList(list)
    private val adapter = Adapter(lists, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // instantiate recyclerview
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_ofLists)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        Log.i("CS3680", "oncreate in main $lists")




        // database Test 1
        lists = getLists()
    }


    override fun onItemClick(position: Int) {
        val intent = Intent(this, ListActivity::class.java)
        val list = lists[position]
        Log.i("CS3680i", " on item click listid: ${list.id}")
        // pass recyclerview position for use of removeAt & other methods
        intent.putExtra("listId", list.id)
        intent.putExtra("listName", list.name)
        //intent.putExtra("name", item.item)
        startActivity(intent)

    }

    // navigate to add item activity
    fun insertList(view: View) {
        val intent = Intent(this, InsertListActivity::class.java)
        startActivity(intent)
    }

    // onresume method reload list data after returning from other view
    override fun onResume() {
        //test()
        super.onResume()
        adapter.notifyDataSetChanged()
    }
    override fun onDeleteButtonClick(position: Int) {
        val myList = lists[position]

        listdbHelper.deleteSingleListRecord(myList.id)
        dbHelper.deleteAllRecordsInList(myList.id)
        Log.i("CS3680i", "on button click list id: ${myList.id}")
        IndividualList.All.removeListById(myList.id)
        adapter.notifyItemRemoved(position)
        Log.i("CS3680", "buttonclicked pos: ${position}")

    }

    // return the list for recyclerview
    fun getLists(): MutableList<IndividualList> {
        lists.removeAll(lists)
        lists = listdbHelper.getLists()
        adapter.notifyDataSetChanged()
        return lists
    }


}