package com.example.cbutcherproject3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbutcherproject3.Item.All.items


class MainActivity : AppCompatActivity(), Adapter.OnItemClickListener, AdapterView.OnItemSelectedListener {


    private var list = getList()
    //private val list = dbHelper.getGroceries()

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
        var spinner : Spinner = findViewById(R.id.spinner)
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
        val dbHelper = GroceryDatabaseHelper(this)
        // database Test 1
        dbHelper.getGroceries()
    }


    override fun onItemClick(position: Int) {
        val intent = Intent(this, ItemActivity::class.java)
        val item = list[position]
        // pass recyclerview position for use of removeAt & other methods
        intent.putExtra("id", item.id)
        //intent.putExtra("name", item.item)

        startActivity(intent)
        adapter.notifyItemChanged(position)
    }

    // user selected an item from the spinner dropdown
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val dbHelper = GroceryDatabaseHelper(this)
        var newList : List<Item>
        // an item was selected. retrieve the selected item with pos
        val item = parent.selectedItem.toString()
        Log.i("CS3680", "$item")
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        dbHelper.queryRecords(item)

        if(item == "All") {
            newList = items.filter {it.category != "Other"}
            recyclerView.setAdapter(Adapter(newList, this))
            recyclerView.invalidate()
            list = newList
            //items = list as MutableList<Item>

        }
        else if(item == "Other") {
            newList = items.filter {it.category != "Produce" && it.category != "Dairy" && it.category != "Pantry" && it.category != "Bakery" && it.category != "Deli"}
            recyclerView.setAdapter(Adapter(newList, this))
            recyclerView.invalidate()
            adapter.notifyDataSetChanged()
            list = newList
            //items = list as MutableList<Item>
        }
        else {
            newList = Item.All.filterItems(item)
            Log.i("CS3680", "${list.size}")
            recyclerView.setAdapter(Adapter(newList, this))
            recyclerView.invalidate()
            adapter.notifyDataSetChanged()
            list = newList
            //items = list as MutableList<Item>
        }
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
        //test()
        super.onResume()
        adapter.notifyDataSetChanged()
        val spinner : Spinner = findViewById(R.id.spinner)
        spinner.setSelection(0)



    }
    // return the list for recyclerview
    private fun getList(): List<Item> {
        return items
    }
    fun test() {
        val dbHelper = GroceryDatabaseHelper(this)
        val db = dbHelper.writableDatabase
        val selectionArgs = arrayOf<String>("PRODUCE")
        val querySQL = "SELECT * FROM Groceries WHERE department = ?;"
        val cursor = db.rawQuery(querySQL, selectionArgs)
        with(cursor) {
            Log.i("CS3680i", "${cursor.getCount()} rows in query result")

            // go through each row in result and do something
            // will need to get values from each row and make an instance of a class
            while(moveToNext()) {
                val action = "do nothing"
                val item = cursor.getString(1)
                Log.i("CS3680i", "$item")
                val quantity = cursor.getString(2)
                val department = cursor.getString(3)
                //groceryList.add(dbHelper.getClassInstance(item, quantity, department, action))
                Log.i("CS3680i", "$item $quantity $department")
            }
        }

    }
}