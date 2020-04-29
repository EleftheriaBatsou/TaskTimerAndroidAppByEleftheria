package com.example.tasktimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import java.lang.Long.getLong

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        val appDatabase = AppDatabase.getInstance(this)
        val db = appDatabase.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM Tasks", null)
        Log.d(TAG, "***********************")
        cursor.use {
            while(it.moveToNext()) {
                with(cursor) {
                    val id = getLong(0)
                    val name = getString(1)
                    val description = getString(2)
                    val sortOrder = getString(3)
                    val result =
                        "ID: $id. Name $name description $description sort order $sortOrder"
                    Log.d(TAG, "on create: reading data $result")
                }
            }
        }

        Log.d(TAG, "***********************")

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
