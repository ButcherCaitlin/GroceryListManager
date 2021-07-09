package com.example.cbutcherproject3
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.cbutcherproject3.GroceryContract.TABLE_NAME
import com.example.cbutcherproject3.ListActivity.Companion.list

// contract for groceries
object GroceryContract : BaseColumns {
    const val TABLE_NAME = "Groceries"
    const val COL_NAME_ID = "id"
    const val COL_NAME_LIST_ID = "listId"
    const val COL_NAME_ITEM = "item"
    const val COL_NAME_QUANTITY = "quantity"
    const val COL_NAME_DEPARTMENT = "department"
}

// database version
const val DATABASE_VERSION = 1

// database name
const val DATABASE_NAME = "groceries.db"

// create table
 val SQL_CREATE_GROC =
        "CREATE TABLE ${GroceryContract.TABLE_NAME} (" +
                "${GroceryContract.COL_NAME_ID} INTEGER PRIMARY KEY," +
                "${GroceryContract.COL_NAME_LIST_ID} INTEGER," +
                "${GroceryContract.COL_NAME_ITEM} TEXT NOT NULL," +
                "${GroceryContract.COL_NAME_QUANTITY} INTEGER," +
                "${GroceryContract.COL_NAME_DEPARTMENT} TEXT NOT NULL)"



// delete table
private val SQL_DELETE_GROC = "DROP TABLE IF EXISTS " + GroceryContract.TABLE_NAME



// delete all
private const val SQL_DELETE_ALL = "DELETE FROM GroceryContract.TABLE_NAME;"



var SQL_ADD_GROC = ""

var groceryList = mutableListOf<GroceryItem>()
// insert records into empty database
val SQL_INSERT_RECORDS = arrayOf(
        "INSERT INTO `Groceries` (listId,item,quantity,department) VALUES(1,'Example Item Here',2,'Category');"
)



data class GroceryItem(val item: String, val quantity: Int, val department: String)


class GroceryDatabaseHelper(context: Context) : SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION


) {

    override fun onCreate(db: SQLiteDatabase) {
        //execSQL executes a single sql statement
        db.execSQL(SQL_CREATE_GROC)
        insertRecords(db)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this database is only a chache for online data so it's upgrade policy is
        //to simply discard the data and start over
        db.execSQL(SQL_DELETE_GROC)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertRecords(db: SQLiteDatabase) {
        for(insert in SQL_INSERT_RECORDS) {
            Log.i("CS3680", insert)
            db.execSQL(insert)
        }


    }

    fun getGroceries(listid: Int) : MutableList<Item> {

        //list = mutableListOf<Item>()
        val select_query = "SELECT * FROM `Groceries` WHERE listId = '$listid'"

        val db = this.writableDatabase
        val cursor = db.rawQuery(select_query, null)
        list.removeAll(list)
        if(cursor.moveToFirst()){
            do{
                //val newList = Item
                var id = cursor.getString(0).toInt()
                var listId = cursor.getString(1).toInt()
                val item = (cursor.getString(2))
                val quantity = (cursor.getString(3).toInt())
                val dept = (cursor.getString(4))
                list.add(Item(id, item, quantity, dept))
                //Item.All.addNew(id, item, quantity, dept)

                //Log.i("CS3680", " new id {$id}")
            }while(cursor.moveToNext())

        }
        return list
    }

    fun getClassInstance(listid: Int, item: String, quantity: Int, department: String, action: String) {
        var grocery = GroceryItem(item, quantity, department)
        if(action == "add"){
            addSingleRecord(listid, grocery)
        }
        if(action == "update") {
            updateSingleRecord(listid, grocery)
        }
    }

    fun addSingleRecord(listid: Int, grocery: GroceryItem){
        val db = this.writableDatabase
        val newItem = grocery.item
        val newQuantity = grocery.quantity
        val newDepartment = grocery.department
        db.execSQL("INSERT INTO `Groceries` (listId, item,quantity,department) VALUES('$listid','$newItem','$newQuantity','$newDepartment');")
    }
    fun updateSingleRecord(updateItemId: Int, grocery: GroceryItem) {
        val db = this.writableDatabase
        val updatedItem = grocery.item
        val updatedQuantity = grocery.quantity
        val updatedDepartment = grocery.department
        db.execSQL("UPDATE `Groceries` SET item = '$updatedItem', quantity = '$updatedQuantity', department = '$updatedDepartment' WHERE id = $updateItemId")
    }
    fun moveItem(listId: Int, itemId: Int) {
        val db = this.writableDatabase
        db.execSQL("UPDATE `Groceries` SET listId = '$listId' where id = $itemId")
    }
    fun deleteSingleRecord(deleteItemId: Int)
    {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM `Groceries` WHERE id = '$deleteItemId'")
    }
    fun deleteAllRecordsInList(listId: Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM `Groceries` WHERE listId = '$listId'")
    }
    fun queryRecords(queryType : String, id: Int) : MutableList<Item> {
        val db = this.writableDatabase
        val selectionArgs = arrayOf<String>("$queryType")
        val select_query = ("SELECT * FROM `Groceries` WHERE department = ? AND listId = $id")
        val cursor = db.rawQuery(select_query, selectionArgs)
        list.removeAll(list)
        if(cursor.moveToFirst()){
            do{
                var id = cursor.getString(0).toInt()
                var listid = cursor.getString(1).toInt()
                val item = (cursor.getString(2))
                val quantity = (cursor.getString(3).toInt())
                val dept = (cursor.getString(4))
                Log.i("CS3680", " queried id:$id item:$item  quantity:$quantity department:$dept")
                list.add(Item(id, item, quantity, dept))
            }while(cursor.moveToNext())
        }
        //var newList: List<Item> = items.filter { it.category == "$queryType" }
        //items = newList as MutableList<Item>
        //return items
        return list
    }
    fun queryOther(queryType: String, id: Int) : MutableList<Item> {
        val db = this.writableDatabase
        val selectionArgs = arrayOf<String>("$id")
        val select_query = ("SELECT * FROM `Groceries` WHERE listId = ? AND department != 'Produce' AND department != 'Deli' AND department != 'Dairy' AND department != 'Pantry' AND department != 'Bakery'")
        val cursor = db.rawQuery(select_query, selectionArgs)
        list.removeAll(list)
        if(cursor.moveToFirst()){
            do{
                var id = cursor.getString(0).toInt()
                var listid = cursor.getString(1).toInt()
                val item = (cursor.getString(2))
                val quantity = (cursor.getString(3).toInt())
                val dept = (cursor.getString(4))
                Log.i("CS3680", " queried id:$id item:$item  quantity:$quantity department:$dept")
                list.add(Item(id, item, quantity, dept))
            }while(cursor.moveToNext())
        }
        //var newList: List<Item> = items.filter { it.category == "$queryType" }
        //items = newList as MutableList<Item>
        //return items
        return list
    }



}