package com.example.cbutcherproject3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cbutcherproject3.Item.All.getItemById
import com.example.cbutcherproject3.Item.All.updateItemById
import com.example.cbutcherproject3.ListActivity.Companion.list

class ItemActivity : AppCompatActivity(), ListAdapter.OnItemClickListener {
    //private val adapter = ListAdapter(Item.itemList, this)
    private lateinit var displayView: TextView
    private lateinit var displayViewQuantity: TextView
    private lateinit var displayViewCategory: TextView

    val adapter = ListAdapter(list, this)
    val dbHelper = GroceryDatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_list_item_detail)


        // get list item
        val itemId = intent.getIntExtra("id", -1)
        val listId = intent.getIntExtra("individualListId", -1)


        val myitem = getItemById(itemId)
        Log.i("CS3680", "Itemactivity OnCreate $myitem")
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

        val removeBtnClick = findViewById<Button>(R.id.removeButton)
        removeBtnClick.setOnClickListener {
            removeItem()
        }
        val editBtnClick = findViewById<Button>(R.id.editItemButton)
        editBtnClick.setOnClickListener{
            editItem(itemId)
        }
        val moveBtnClick = findViewById<Button>(R.id.moveItemButton)
        moveBtnClick.setOnClickListener {
            moveItem(itemId)
        }
    }
    // remove this item from the recyclerview list
    fun removeItem() {
        val position = intent.getIntExtra("position", -1)
        val itemId = intent.getIntExtra("id", -1)
        Log.i("CS3680", "item to delete $itemId")
        //Item.All.removeItemById(itemId)
        dbHelper.deleteSingleRecord(itemId)
        Item.All.removeItemById(itemId)
        adapter.notifyItemRemoved(position)
        finish()
    }

    // edit this item
    fun editItem(itemId: Int) {
        displayView = findViewById(R.id.singleItem)
        displayViewQuantity = findViewById(R.id.singleQuantity)
        displayViewCategory = findViewById(R.id.singleCategory)

        var myItem = displayView.text.toString()
        var myQuantity = displayViewQuantity.text.toString().toInt()
        var myCategory = displayViewCategory.text.toString()

        val editBtn = findViewById<Button>(R.id.editItemButton)
        if(editBtn.text == "Edit Item") {
            editBtn.setText("Save")
            displayView.setBackgroundColor(Color.parseColor("#E8F9F8"))
            displayViewQuantity.setBackgroundColor(Color.parseColor("#E8F9FE"))
            displayViewCategory.setBackgroundColor(Color.parseColor("#E8F9FE"))
            displayView.text = myItem
            displayViewQuantity.text = myQuantity.toString()
            displayViewCategory.text = myCategory

        }
        else if(editBtn.text == "Save") {
            editBtn.setText("Edit Item")
            displayView.setBackgroundColor(0)
            displayViewQuantity.setBackgroundColor(0)
            displayViewCategory.setBackgroundColor(0)
            displayView.clearFocus()
            displayViewQuantity.clearFocus()
            displayViewCategory.clearFocus()
            dbHelper.getClassInstance(itemId, myItem, myQuantity, myCategory, "update")
            updateItemById(itemId, myItem, myQuantity, myCategory)
            displayView.text = myItem
            displayViewQuantity.text = myQuantity.toString()
            displayViewCategory.text = myCategory
            adapter.notifyItemChanged(itemId)
        }
    }

    // move this item to a different list
    fun moveItem(itemId: Int) {
        val position = intent.getIntExtra("position", -1)
        val listId = intent.getIntExtra("indListId", -1)
        val intent = Intent(this, MoveItemActivity::class.java)
        intent.putExtra("itemToMove", itemId)
        Log.i("CS3680", "itemactivitylistid: ${listId}")
        intent.putExtra("moveTo", listId)
        startActivity(intent)
        Item.All.removeItemById(itemId)
        adapter.notifyItemRemoved(position)
        adapter.notifyDataSetChanged()
        finish()
    }
    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}