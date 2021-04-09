package com.example.room.mvvm.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.room.mvvm.model.LoginTableModel

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(loginTableModel: LoginTableModel)

    @Query("SELECT * FROM Login WHERE Username =:username")
    fun getLoginDetails(username: String?) : LiveData<LoginTableModel>

    @Query("SELECT * FROM Login")
    fun getAllLoginDetails():LiveData<List<LoginTableModel>>

    @Query("DELETE   FROM Login WHERE Id=:id")
    suspend fun deleteLoginData(id: Int):Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateData(loginDetails: LoginTableModel)
}