package com.example.room.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.room.LoginDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LoginRepository() {

    companion object {


        private var loginTableModel: LiveData<LoginTableModel>? = null
        private var allLogins: LiveData<List<LoginTableModel>>? = null


        fun insertData(loginDatabase: LoginDatabase, username: String, password: String,age:Int) {


            CoroutineScope(IO).launch {
                val loginDetails = LoginTableModel(0,username, password,age)
                loginDatabase.loginDao().InsertData(loginDetails)
            }

        }

        fun getLoginDetails(
            loginDatabase: LoginDatabase,
            username: String
        ): LiveData<LoginTableModel>? {


            loginTableModel = loginDatabase.loginDao().getLoginDetails(username)

            return loginTableModel
        }

        fun getAllDetails(loginDatabase: LoginDatabase): LiveData<List<LoginTableModel>>? {
            allLogins = loginDatabase.loginDao().getAllLoginDetails()
            return allLogins
        }

        fun deleteLoginDAta(loginDatabase: LoginDatabase,id: Int):Int{
            var integer:Int=-1
            CoroutineScope(IO).launch {
              integer  = loginDatabase.loginDao().deleteLoginData(id)
            }
            return integer
        }

        fun updateData(loginDatabase: LoginDatabase, id: Int, username: String, password: String,age:Int) {
            CoroutineScope(IO).launch {
                val loginDetails = LoginTableModel(id,username, password,age)
                Log.d("TAG", "updateData:$loginDetails ")
                loginDatabase.loginDao().updateData(loginDetails)
            }
        }

    }
}