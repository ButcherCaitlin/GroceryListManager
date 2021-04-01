package com.example.cbutcherproject3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cbutcherproject3.Item.All.items


class AddItemActivity : AppCompatActivity(), Adapter.OnItemClickListener{
    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
    private lateinit var addGroceryItem: EditText
    private lateinit var quantity: EditText
    private lateinit var category: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)
        addGroceryItem = findViewById(R.id.item)
        quantity = findViewById(R.id.quantity)
        category = findViewById(R.id.category)

        val doneButton: Button = findViewById(R.id.done)
        doneButton.setOnClickListener { addItem()
            Log.i("additem", "list")
            Log.i("CS3680", "$quantity")
        }


    }

    // add item to recyclerview list
    private fun addItem() {
        val item = addGroceryItem.text.toString()
        val itemQuantity = quantity.text.toString().toInt()
        Log.i("CS3680", "add itemQuantity $itemQuantity")
        val itemCategory = category.text.toString()

       Item.All.addNew(items.lastIndex + 1, item, itemQuantity, itemCategory)
        finish()


    }
}