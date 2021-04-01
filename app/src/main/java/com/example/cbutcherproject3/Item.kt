package com.example.cbutcherproject3

import android.text.TextUtils.indexOf
import android.util.Log
import android.widget.EditText

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cbutcherproject3.Item.All.items

class Item(var id: Int, var item: String, var quantity: Int, var category: String) {

companion object All {

    // list of Items
    var itemList = listOf(
        Item(
            id = 1,
            item = "Oranges",
            quantity = 2,
            category = "Produce"
        ),
        Item(
            id = 2,
            item = "Bread",
            quantity = 1,
            category = "Bakery"
        ),
        Item(
            id = 3,
            item = "Peanut Butter",
            quantity = 1,
            category = "Pantry"
        ),
        Item(
            id = 4,
            item = "Jelly",
            quantity = 1,
            category = "Pantry"
        ),
        Item(
            id = 5,
            item = "Turkey",
            quantity = 1,
            category = "Deli"
        ),
        Item(
            id = 6,
            item = "Zucchini",
            quantity = 2,
            category = "Produce"
        ),
        Item(
            id = 7,
            item = "Asparagus",
            quantity = 1,
            category = "Produce"
        ),
        Item(
            id = 8,
            item = "Bell Peppers",
            quantity = 3,
            category = "Produce"
        ),
        Item(
            id = 9,
            item = "Pre-made Salads",
            quantity = 2,
            category = "Produce"
        ),
        Item(
            id = 10,
            item = "Carrots",
            quantity = 3,
            category = "Produce"
        ),
        Item(
            id = 11,
            item = "Brussel Sprouts",
            quantity = 10,
            category = "Produce"
        ),
        Item(
            id = 12,
            item = "Juice",
            quantity = 1,
            category = "Pantry"
        ),
        Item(
            id = 13,
            item = "Pasta",
            quantity = 1,
            category = "Pantry"
        ),
        Item(
            id = 14,
            item = "Milk",
            quantity = 1,
            category = "Dairy"
        ),
        Item(
            id = 15,
            item = "Sour Cream",
            quantity = 1,
            category = "Dairy"
        ),
        Item(
            id = 16,
            item = "Chicken",
            quantity = 1,
            category = "Meat & Seafood"
        ),
        Item(
            id = 17,
            item = "Tortillas",
            quantity = 1,
            category = "Bakery"
        ),
        Item(
            id = 18,
            item = "Enchilada Sauce",
            quantity = 1,
            category = "Pantry"
        ),
        Item(
            id = 19,
            item = "Dog Food",
            quantity = 1,
            category = "Pets"
        ),
        Item(
            id = 20,
            item = "Dr. Pepper",
            quantity = 1,
            category = "Beverages"
        ),
        Item(
            id = 21,
            item = "Bananas",
            quantity = 5,
            category = "Produce"
        ),
        Item(
            id = 22,
            item = "Potatoes",
            quantity = 5,
            category = "Produce"
        )
    )
    val items = makeList()

    // make the list into a new mutable list of type Item & add all items to it
    fun makeList(): MutableList<Item> {

        var listMake = mutableListOf<Item>()
        for(item in itemList) {
            listMake.add(Item(item.id, item.item, item.quantity, item.category))
        }

        return listMake
    }

    // add a new Item to the list
    fun addNew(id: Int, newItem: String, newItemQuantity: Int, newItemCategory: String): List<Item> {
            items.add(Item(id, newItem, newItemQuantity, newItemCategory))
        itemList = items
        return items
    }

    // get individual list item with ID of item
    fun getItemById(idNum: Int): MutableList<Item> {
        val listItems = mutableListOf<Item>()
            listItems.add(Item(itemList[idNum].id, itemList[idNum].item, itemList[idNum].quantity, itemList[idNum].category))
        return listItems
    }
    // removes item by item name
    fun removeItemByPosition(pos: Int): List<Item> {
        items.removeAt(pos)
        itemList = items
        return itemList
    }
}
}
