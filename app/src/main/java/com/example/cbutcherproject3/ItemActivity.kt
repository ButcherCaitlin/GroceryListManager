package com.example.cbutcherproject3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cbutcherproject3.Item.All.getItemById

class ItemActivity : AppCompatActivity(), Adapter.OnItemClickListener {
    private val adapter = Adapter(Item.itemList, this)
    private lateinit var displayView: TextView
    private lateinit var displayViewQuantity: TextView
    private lateinit var displayViewCategory: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_item)

        // get list item
        val listItem = intent.getIntExtra("position", -1)
        Log.i("CS3680", "Itemactivity OnCreate $listItem")


        val myitem = getItemById(listItem)
        Log.i("CS3680", "my item ${myitem}")
        displayView = findViewById(R.id.singleItem)
        displayViewQuantity = findViewById(R.id.singleQuantity)
        displayViewCategory = findViewById(R.id.singleCategory)
        val myItems = myitem[0].item
        val myQuantity = myitem[0].quantity
        val myCategory = myitem[0].category
        Log.i("CS3680", "$myItems")
        displayView.text = myItems.toString()
        displayViewQuantity.text = myQuantity.toString()
        displayViewCategory.text = myCategory.toString()
        val btnClick = findViewById<Button>(R.id.removeButton)
        btnClick.setOnClickListener {
            removeItem()
        }
    }
    // remove this item from the recyclerview list
    fun removeItem() {
        val listPosition = intent.getIntExtra("position", -1)
        val listItemRemove = intent.getStringExtra("itemtoremove")
        Log.i("CS3680", "remove item in ItemActivity intent value $listItemRemove" )
        Item.All.removeItemByName(listItemRemove, listPosition)
        finish()
    }
    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}