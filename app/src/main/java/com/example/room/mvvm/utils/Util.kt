package com.example.room.mvvm.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.room.mvvm.R


class Util {
    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Login ADD COLUMN lastname TEXT NOT NULL DEFAULT ''")
            }
        }

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun getAlertDialog(context: Context): Dialog {
            val factory = LayoutInflater.from(context)
            val dialogView: View = factory.inflate(R.layout.alert_dialog, null)
            return Dialog(context).apply {
                setCancelable(false)
                setContentView(dialogView)
                create()
            }
        }
    }
}