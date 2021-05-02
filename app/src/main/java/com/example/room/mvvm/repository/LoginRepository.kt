package com.example.room.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.room.DAOAccess
import com.example.room.mvvm.room.LoginDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginRepository @Inject constructor(private val daoAccess: DAOAccess) {

        private var loginTableModel: LiveData<LoginTableModel>? = null
        private var allLogins: LiveData<List<LoginTableModel>>? = null


        fun insertData( username: String,lastName:String, password: String,age:Int) {


            CoroutineScope(IO).launch {
                val loginDetails = LoginTableModel(0,username,lastName, password,age)
                daoAccess.InsertData(loginDetails)
            }

        }

        fun getLoginDetails(
            username: String
        ): LiveData<LoginTableModel>? {


            loginTableModel = daoAccess.getLoginDetails(username)

            return loginTableModel
        }

        fun getAllDetails(): LiveData<List<LoginTableModel>>? {
            allLogins = daoAccess.getAllLoginDetails()
            return allLogins
        }

        fun deleteLoginDAta(id: Int):Int{
            var integer:Int=-1
            CoroutineScope(IO).launch {
              integer  = daoAccess.deleteLoginData(id)
            }
            return integer
        }

        fun updateData(id: Int, username: String,lastName: String, password: String,age:Int) {
            CoroutineScope(IO).launch {
                val loginDetails = LoginTableModel(id,username,lastName, password,age)
                Log.d("TAG", "updateData:$loginDetails ")
                daoAccess.updateData(loginDetails)
            }
        }


}