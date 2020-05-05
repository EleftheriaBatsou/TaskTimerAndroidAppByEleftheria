package com.example.tasktimer

import android.content.res.Configuration
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity()
        , AddEditFragment.OnSaveClicked,
        MainActivityFragment.OnTaskEdit {


    // Whether or the activity is in 2-pane mode
    // i.e. running in landscape, or on a tablet.
    private var mTwoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        mTwoPane = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Log.d(TAG, "onCreate: twoPane is $mTwoPane")

        val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        if(fragment != null) {
            // There was an existing fragment to edit a task, make sure the panes are set correctly
            showEditPane()
        } else {
            task_details_container.visibility = if(mTwoPane) View.INVISIBLE else View.GONE
            mainFragment.view?.visibility = View.VISIBLE
        }
        Log.d(TAG, "onCreate: finished")
    }

    private fun showEditPane() {
        task_details_container.visibility = View.VISIBLE
        // hide the left hand pane, if in single pane view
        mainFragment.view?.visibility = if(mTwoPane) View.VISIBLE else View.GONE

    }

    private fun removeEditPane(fragment: Fragment? = null) {
        Log.d(TAG, "removeEditPane called")
        if (fragment!=null) {
            supportFragmentManager.beginTransaction()
                    .remove(fragment)
                .commit()
        }

        // Set the visibility of the right hand pane
        task_details_container.visibility = if(mTwoPane) View.INVISIBLE else View.GONE
        // and show the left hand pane
        mainFragment.view?.visibility = View.VISIBLE

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onSaveClicked() {
        Log.d(TAG, "onSaveClicked: called")
        val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        removeEditPane(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menumain_addTask -> taskEditRequest(null)
//            R.id.menumain_settings -> true
            android.R.id.home -> {
                Log.d(TAG, "onOptionsItemSelected: home button pressed")
                val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
                removeEditPane(fragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTaskEdit(task: Task) {
        taskEditRequest(task)
    }

    private fun taskEditRequest(task: Task?) {
        Log.d(TAG, "taskEditRequest: starts")

        // Create a new fragment to edit the task
        val newFragment = AddEditFragment.newInstance(task)
        supportFragmentManager.beginTransaction()
                .replace(R.id.task_details_container, newFragment)
                .commit()

        showEditPane()

        Log.d(TAG, "Exiting taskEditRequest")
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        if (fragment == null || mTwoPane) {
            super.onBackPressed()
        } else {
            removeEditPane(fragment)
        }
    }

    override fun onStart() {
        Log.d(TAG, "onStart: called")
        super.onStart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        Log.d(TAG, "onRestoreInstanceState: called")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        Log.d(TAG, "onResume: called")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: called")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState: called")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(TAG, "onStop: called")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }
}
