package com.example.cbutcherproject3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbutcherproject3.Item.All.itemList
import com.example.cbutcherproject3.Item.All.items


class MainActivity : AppCompatActivity(), Adapter.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private val list = getList()
    //private var mutableList =  Item.makeList(list)
    private val adapter = Adapter(list, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // instantiate recyclerview
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        Log.i("CS3680", "oncreate in main $list")

        // instantiate spinner
        val spinner : Spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.departments_array,
                android.R.layout.simple_spinner_item
        ).also {
            adapter ->
            // specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // apply the adapter to the spinner
            spinner.adapter = adapter
        }

        // database Test 1
        val dbHelper = GroceryDatabaseHelper(this)
        val db = dbHelper.writableDatabase
        val selectionArgs = arrayOf<String>("Produce")

        // all cols in result, the ? is a placeholder & will substitute in the values
        // must provide a value for each ?
        val querySQL = "SELECT * FROM Groceries WHERE department = ?;"
        val cursor = db.rawQuery(querySQL, selectionArgs)
        with(cursor) {
            Log.i("CS3680", "${cursor.getCount()} rows in query result")

            // go through each row in result and do something
            // will need to get values from each row and make an instance of a class
            while(moveToNext()) {
                val item = cursor.getString(1)
                val quantity = cursor.getString(2)
                val department = cursor.getString(3)
                Log.i("CS3680", "$item $quantity $department")
            }
        }
    }


    override fun onItemClick(position: Int) {
        val intent = Intent(this, ItemActivity::class.java)
        // pass recyclerview position for use of removeAt & other methods
        intent.putExtra("position", position)

        startActivity(intent)
        adapter.notifyItemChanged(position)
    }

    // user selected an item from the spinner dropdown
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // an item was selected. retrieve the selected item with
        parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
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