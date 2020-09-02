package com.cavista.imagesearchingapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cavista.imagesearchingapp.Model.ImageData

private val DATABASE_VERSION = 1
private val DATABASE_NAME = "ImageSeachDB.db"

val TABLE_IMAGE = "ImageDetails"

val IMAGE_ID = "_id"
val IMAGE_TITLE = "title"
val IMAGE_COMMENT = "comment"

class DatabaseHandlerHandler(context: Context, name: String?,
                             factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, DATABASE_NAME,
                factory, DATABASE_VERSION) {



    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_IMAGE_TABLE = ("CREATE TABLE " +
                TABLE_IMAGE + "("
                + IMAGE_ID + " TEXT," +
                IMAGE_TITLE
                + " TEXT," + IMAGE_COMMENT + " TEXT" + ")")
        db.execSQL(CREATE_IMAGE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE)
        onCreate(db)

    }

    fun addProduct(image: ImageData) {

        val values = ContentValues()
        values.put(IMAGE_ID, image.id)
        values.put(IMAGE_TITLE, image.tital)
        values.put(IMAGE_COMMENT, image.comment)

        val db = this.writableDatabase

        db.insert(TABLE_IMAGE, null, values)
        db.close()
    }

    fun findImageComment(imageId: String): String {
        val query =
                "SELECT * FROM $TABLE_IMAGE WHERE $IMAGE_ID=\"$imageId\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)


        var comment: String =""
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            comment = cursor.getString(cursor.getColumnIndex(IMAGE_COMMENT))
            cursor.close()
        }

        db.close()
        return comment
    }

}