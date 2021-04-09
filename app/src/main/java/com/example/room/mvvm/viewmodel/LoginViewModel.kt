package com.example.room.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.repository.LoginRepository
import com.example.room.mvvm.room.LoginDatabase

class LoginViewModel() : ViewModel() {

    private var liveDataLogin: LiveData<LoginTableModel>? = null
    private var allDetails: LiveData<List<LoginTableModel>>? = null

    fun insertData(loginDatabase: LoginDatabase, username: String, password: String) {
        LoginRepository.insertData(loginDatabase, username, password)
        getAllDetails(loginDatabase)
    }
    fun updateData(loginDatabase: LoginDatabase,id: Int, username: String, password: String) {
        LoginRepository.updateData(loginDatabase,id, username, password)
        getAllDetails(loginDatabase)
    }

    fun getLoginDetails(
        loginDatabase: LoginDatabase,
        username: String
    ): LiveData<LoginTableModel>? {
        liveDataLogin = LoginRepository.getLoginDetails(loginDatabase, username)
        return liveDataLogin
    }

    fun getAllDetails(loginDatabase: LoginDatabase): LiveData<List<LoginTableModel>>? {
        allDetails = LoginRepository.getAllDetails(loginDatabase)
        return allDetails
    }

    fun deleteLoginUser(loginDatabase: LoginDatabase, id: Int): Int? {
        Log.d("TAG", "deleteLoginUserB: ")
        val result = LoginRepository.deleteLoginDAta(loginDatabase, id)
        Log.d("TAG", "deleteLoginUser: ")
        if(result>0){
           getAllDetails(loginDatabase)
        }
        return result
    }

}