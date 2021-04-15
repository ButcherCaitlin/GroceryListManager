package com.example.cbutcherproject3
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cbutcherproject3.GroceryContract.TABLE_NAME
import com.example.cbutcherproject3.Item.All.items
import com.example.cbutcherproject3.MainActivity
// contract
object GroceryContract : BaseColumns {
    const val TABLE_NAME = "Groceries"
    const val COL_NAME_ID = "id"
    const val COL_NAME_ITEM = "item"
    const val COL_NAME_QUANTITY = "quantity"
    const val COL_NAME_DEPARTMENT = "department"
}

// database version
const val DATABASE_VERSION = 1

// database name
const val DATABASE_NAME = "groceries.db"

// create table
private const val SQL_CREATE_GROC =
        "CREATE TABLE ${GroceryContract.TABLE_NAME} (" +
                "${GroceryContract.COL_NAME_ID} INTEGER PRIMARY KEY," +
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
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Oranges',2,'Produce');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Bread',1,'Bakery');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Peanut Butter',1,'Pantry');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Jelly',1,'Pantry');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Turkey',1,'Deli');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Zucchini',2,'Produce');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Bell Peppers',3,'Produce');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Pre-made Salads',2,'Produce');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Carrots',3,'Produce');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Brussel Sprouts',10,'Produce');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Juice',1,'Pantry');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Pasta',1,'Pantry');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Milk',1,'Dairy');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Sour Cream',1,'Dairy');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Chicken',1,'Meat & Seafood');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Tortillas',1,'Bakery');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Enchilada Sauce',1,'Pantry');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Dog Food',1,'Pets');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Dr. Pepper',1,'Beverages');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Bananas',5,'Produce');",
        "INSERT INTO `Groceries` (item,quantity,department) VALUES('Paperclips', 1, 'Office');"
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
        //getGroceries()

    }

    fun getGroceries() : List<Item> {

        val list = listOf<Item>()
        val select_query = "SELECT * FROM " + TABLE_NAME

        val db = this.writableDatabase
        val cursor = db.rawQuery(select_query, null)

        if(cursor.moveToFirst()){
            do{
                val newItem = Item
                var id = cursor.getString(0).toInt()
                val item = (cursor.getString(1))
                val quantity = (cursor.getString(2).toInt())
                val dept = (cursor.getString(3))
                Item.All.addNew(id, item, quantity, dept)
                Log.i("CS3680", " new id {$id}")
            }while(cursor.moveToNext())

        }
        return list
    }
    fun getClassInstance(item: String, quantity: Int, department: String, action: String) {
        var grocery = GroceryItem(item, quantity, department)
        if(action == "add"){
            addSingleRecord(grocery)
        }
    }

    fun addSingleRecord(grocery: GroceryItem){
        val db = this.writableDatabase
        val newItem = grocery.item
        val newQuantity = grocery.quantity
        val newDepartment = grocery.department
        db.execSQL("INSERT INTO `Groceries` (item,quantity,department) VALUES('$newItem','$newQuantity','$newDepartment');")
    }
    fun deleteSingleRecord(deleteItemId: Int)
    {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM `Groceries` WHERE id = '$deleteItemId'")

    }
    fun queryRecords(queryType : String) {
        val db = this.writableDatabase
        val selectionArgs = arrayOf<String>("$queryType")
        val select_query = ("SELECT * FROM `Groceries` WHERE department = ?")
        val cursor = db.rawQuery(select_query, selectionArgs)

        if(cursor.moveToFirst()){
            do{
                var id = cursor.getString(0).toInt()
                val item = (cursor.getString(1))
                val quantity = (cursor.getString(2).toInt())
                val dept = (cursor.getString(3))
                Log.i("CS3680", " queried id:$id item:$item  quantity:$quantity department:$dept")
            }while(cursor.moveToNext())
        }
        //var newList: List<Item> = items.filter { it.category == "$queryType" }
        //items = newList as MutableList<Item>
        //return items

    }




}