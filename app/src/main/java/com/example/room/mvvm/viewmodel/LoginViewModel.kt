package com.example.room.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.repository.LoginRepository
import com.example.room.mvvm.room.LoginDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private var liveDataLogin: LiveData<LoginTableModel>? = null
    private var allDetails: LiveData<List<LoginTableModel>>? = null

    fun insertData( username: String,lastName:String, password: String,age:Int) {
        repository.insertData( username,lastName, password,age)
        getAllDetails()
    }
    fun updateData(id: Int, username: String,lastName: String, password: String,age:Int) {
        repository.updateData(id, username,lastName, password,age)
        getAllDetails()
    }

    fun getLoginDetails(
        username: String
    ): LiveData<LoginTableModel>? {
        liveDataLogin = repository.getLoginDetails( username)
        return liveDataLogin
    }

    fun getAllDetails(): LiveData<List<LoginTableModel>>? {
        allDetails = repository.getAllDetails()
        return allDetails
    }

    fun deleteLoginUser(id: Int): Int {
        Log.d("TAG", "deleteLoginUserB: ")
        val result = repository.deleteLoginDAta(id)
        Log.d("TAG", "deleteLoginUser: ")
        if(result>0){
           getAllDetails()
        }
        return result
    }

}