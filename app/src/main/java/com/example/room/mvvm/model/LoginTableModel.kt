package com.example.room.mvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Login")
data class LoginTableModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val Id: Int,

    @ColumnInfo(name = "username")
    val Username: String,

    @ColumnInfo(name = "lastname")
    val LastName: String,

    @ColumnInfo(name = "password")
    val Password: String,

    @ColumnInfo(name = "age")
    val age: Int

) {

    var isEditing: Boolean = false

    constructor(id: Int) : this(id, "", "", "", 0)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginTableModel

        if (Id != other.Id) return false

        return true
    }

    override fun hashCode(): Int {
        return Id
    }

}