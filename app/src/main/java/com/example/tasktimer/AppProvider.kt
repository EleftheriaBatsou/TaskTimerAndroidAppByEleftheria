package com.example.tasktimer

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log

/**
 * Provider for the TaskTimer app. This is the only class that know about [AppDatabase]
 *
 * */

private const val TAG = "AppProvider"

private val CONTENT_AUTHORITY = "com.example.tasktimer.provider"

private const val TASKS = 100
private const val TASKS_ID = 101

private const val TIMINGS = 200
private const val TIMINGS_ID = 201

private const val TASK_DURATIONS = 400
private const val TASK_DURATIONS_ID = 401

val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")

class AppProvider: ContentProvider() {
    private val uriMatcher by lazy { buildUriMatcher() }

    private fun buildUriMatcher() : UriMatcher {
        Log.d(TAG, "build uri matcher starts")
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        // e.g. content: com.example.tasktimer.provider/Tasks
        // e.g. content: learnprogramming.academy.tasktimer.provider/Tasks
        matcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME, TASKS)

        // e.g. content: com.example.tasktimer.provider/Tasks/7
        matcher.addURI(CONTENT_AUTHORITY, "${TasksContract.TABLE_NAME}/#", TASKS_ID)

//        matcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME, TIMINGS)
//        matcher.addURI(CONTENT_AUTHORITY, "${TimingsContract.TABLE_NAME}/#", TIMINGS_ID)
//
//        matcher.addURI(CONTENT_AUTHORITY, DurationContract.TABLE_NAME, TASK_DURATIONS)
//        matcher.addURI(CONTENT_AUTHORITY, "${DurationContract.TABLE_NAME}/#", TASK_DURATIONS_ID)
        return matcher
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "onCreate: starts")
        return true
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d(TAG, "query: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "query match is $match")

        val queryBuilder = SQLiteQueryBuilder()

        when (match) {
            TASKS -> queryBuilder.tables = TasksContract.TABLE_NAME

            TASKS_ID -> {
                queryBuilder.tables = TasksContract.TABLE_NAME
                val taskId = TasksContract.getId(uri)
                queryBuilder.appendWhereEscapeString("${TasksContract.Columns.ID} = $taskId")       // <-- change method
            }

//            TIMINGS -> queryBuilder.tables = TimingsContract.TABLE_NAME
//
//            TIMINGS_ID -> {
//                queryBuilder.tables = TimingsContract.TABLE_NAME
//                val timingId = TimingsContract.getId(uri)
//                queryBuilder.appendWhereEscapeString("${TimingsContract.Columns.ID} = $timingId")   // <-- and here
//            }
//
//            TASK_DURATIONS -> queryBuilder.tables = DurationsContract.TABLE_NAME
//
//            TASK_DURATIONS_ID -> {
//                queryBuilder.tables = DurationsContract.TABLE_NAME
//                val durationId = DurationsContract.getId(uri)
//                queryBuilder.appendWhereEscapeString("${DurationsContract.Columns.ID} = $durationId")   // <-- and here
//            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val db = AppDatabase.getInstance(context).readableDatabase
        val cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        Log.d(TAG, "query: rows in returned cursor = ${cursor.count}") // TODO remove this line

        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "insert: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "insert: match is $match")

        val recordId: Long
        val returnUri: Uri

        val context = context ?: throw NullPointerException("Context can't be null here!")

        when (match){

        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}