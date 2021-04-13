package com.example.room.mvvm.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.room.mvvm.model.LoginTableModel

@Database(entities = arrayOf(LoginTableModel::class), version = 2, exportSchema = false)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun loginDao() : DAOAccess

    companion object {

        @Volatile
        private var INSTANCE: LoginDatabase? = null

        fun getDataseClient(context: Context) : LoginDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, LoginDatabase::class.java, "LOGIN_DATABASE")
                    .addMigrations(MIGRATION_1_2)
                    .build()

                return INSTANCE!!

            }
        }

         val MIGRATION_1_2 =object :Migration(1,2){
             override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Login ADD COLUMN age INT(2) NOT NULL DEFAULT ''")
             }
         }
    }

}