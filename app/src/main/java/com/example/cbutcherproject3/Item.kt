package com.example.cbutcherproject3

import com.example.cbutcherproject3.ListActivity.Companion.list

class Item(var id: Int, var item: String, var quantity: Int, var category: String) {

companion object All {


    // add a new Item to the list
    fun addNew(id: Int, newItem: String, newItemQuantity: Int, newItemCategory: String): List<Item> {
            list.add(Item(id, newItem, newItemQuantity, newItemCategory))
        //itemList = items
        return list

    }

    // get individual list item with ID of item
    fun getItemById(id: Int): List<Item> {
        //val listItems = mutableListOf<Item>()
            //listItems.add(Item(items[idNum].id, items[idNum].item, items[idNum].quantity, items[idNum].category))
        val item =  list.filter {it.id == id}
        return item
    }
    fun updateItemById(id: Int, item: String, quantity: Int, category: String) {
        list?.find{it.id == id}?.item = item
        list?.find{it.id ==id}?.quantity = quantity
        list?.find{it.id == id}?.category = category
    }
    // removes item by item position
    fun removeItemById(id: Int): List<Item> {
        val item = list.filter{it.id == id}

        list.removeAll(item)
        return list
    }
}
}
