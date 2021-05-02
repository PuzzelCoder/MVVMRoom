package com.example.room.mvvm.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.mvvm.R
import com.example.room.mvvm.utils.Util.Companion.hideKeyboard
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.room.LoginDatabase
import com.example.room.mvvm.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserAdapter.ClickInterface {

    lateinit var loginViewModel: LoginViewModel
    lateinit var loginDatabase: LoginDatabase
    lateinit var context: Context
    lateinit var usersList: List<LoginTableModel>

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersList = arrayListOf()
        userAdapter = UserAdapter(usersList, this)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = userAdapter
        context = this@MainActivity

        loginDatabase = initializeDB(applicationContext)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        btnAddLogin.setOnClickListener {

            val strUsername = txtUsername.text.toString().trim()
            val strPassword = txtPassword.text.toString().trim()
            val strAge = txtage.text.toString().trim()

            if (strPassword.isEmpty()) {
                txtUsername.error = "Please enter the username"
            } else if (strPassword.isEmpty()) {
                txtPassword.error = "Please enter the username"
            } else {
                loginViewModel.insertData(loginDatabase, strUsername, strPassword,strAge.toInt())

                lblInsertResponse.text = "Inserted Successfully"
                txtUsername.text = null
                txtPassword.text = null
            }

            hideKeyboard(it)

        }

        setObservers()
//        btnEditLogin.setOnClickListener {
//            btnEditLogin.visibility= View.GONE
//
//            val strUserId: Int = txtUserid.text.toString().trim().toInt()
//            strUsername = txtUsername.text.toString().trim()
//            strPassword = txtPassword.text.toString().trim()
//            if (strUserId < 0) {
//                lblInsertResponse.text = "Sorry ,Something went wrong"
//            } else if (strPassword.isEmpty()) {
//                txtUsername.error = "Please enter the username"
//            } else if (strPassword.isEmpty()) {
//                txtPassword.error = "Please enter the password"
//            } else {
//                loginViewModel.editData(loginDatabase, strUserId, strUsername, strPassword)
//
//                lblInsertResponse.text = "Edited Successfully"
//                txtUsername.text = null
//                txtPassword.text = null
//            }
//        }

        btnReadLogin.setOnClickListener {
            loginViewModel.getAllDetails(loginDatabase)
//            strUsername = txtUsername.text.toString().trim()

//            loginViewModel.getLoginDetails(loginDatabase, strUsername)!!.observe(this, Observer {
//
//                if (it == null) {
//                    lblReadResponse.text = "Data Not Found"
//                    lblUseraname.text = "- - -"
//                    lblPassword.text = "- - -"
//                } else {
//                   lblUseraname.text = it.Username
//                    lblPassword.text = it.Password
//                    lblReadResponse.text = "Data Found Successfully"
//                }
//            })
//            MVVM_Room_Kotlin_Example

        }
    }


    fun setObservers(){
        loginViewModel.getAllDetails(loginDatabase)!!.observe(this, Observer {
            if (it == null || (it as List<LoginTableModel>).size == 0) {
                val s = (it as List<LoginTableModel>).size
                Log.d("TAG", "onCreate3: $s")
                lblReadResponse.text = "Data Not Found"
                recyclerView.visibility = View.GONE
                lblReadResponse.visibility = View.VISIBLE
            } else {
                val loginList: List<LoginTableModel> = it
                Log.d("TAG", "onCreate4: $loginList")
//                    Log.d("User${loginList.get(0).Id}", "name=${loginList.get(0).Username}")
//                    Log.d("User${loginList.get(0).Id}", "name=${loginList.get(0).Password}")
                for (i in loginList) {
                    Log.d("User${i.Id}", "name=${i.Username}")
                    Log.d("User${i.Id}", "name=${i.Password}")
                }
                (usersList as ArrayList).clear()
                (usersList as ArrayList).addAll(it)
                userAdapter.notifyDataSetChanged()
                lblReadResponse.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        })
    }
    fun initializeDB(context: Context): LoginDatabase {
        return LoginDatabase.getDataseClient(context)
    }

    override fun onEditClick(view: View, id: Int,name: String, pass: String,age:Int,  editing: Boolean) {
        hideKeyboard(view)
        Log.d("TAG", "Edit Clicked at position $id: ")
        val user =LoginTableModel(id,"","",0)
        val index= usersList.indexOf(user)
        if(index<0){
            Log.d("TAG", "onEditClick: User not found")
        }
        else{
            usersList.get(index).isEditing =! editing
            userAdapter.notifyDataSetChanged()

            if(editing){
                loginViewModel.updateData(loginDatabase,id,name,pass,age)
                Log.d("TAG", "onDoneClick: ")

            }

        }

    }
//    override fun onEditClick( view: View, id: Int, name: String, pass: String,isEditing: Boolean) {
//        Log.d("TAG", "Edit Clicked at position $id: ")
//
//        loginViewModel.updateData(loginDatabase, id,name, pass)
//    }

    override fun onDeleteItem(it: View, id: Int) {
        Log.d("TAG", "Delete Clicked at position $id: ")
        val result: Int? = loginViewModel.deleteLoginUser(loginDatabase, id)
        if (result!! > 0) {
            Toast.makeText(context, "User deleted succssfully", Toast.LENGTH_SHORT).show()
        }
    }


//
//    override fun onEditClick(view: View, position: Int) {
//        TODO("Not yet implemented")
//    }(view: View, id: Int) {
//        when (view.id) {
//            R.id.delete -> {
//                Log.d("TAG", "Delete Clicked at position $id: ")
//                val result: Int? = loginViewModel.deleteLoginUser(loginDatabase, id)
//                if (result!! > 0) {
//                    Toast.makeText(context, "User delete succssfully", Toast.LENGTH_SHORT).show()
//
//                }
//            }
//            R.id.edit -> {
//                Log.d("TAG", "Edit Clicked at position $id: ")
//                val user = LoginTableModel(id)
//                val index = usersList.indexOf(user)
//                if (index != -1) {
//                    val user = usersList.get(index)
//                    Log.d("TAG", "onSingleClick: $id")
////                    txtUserid.setText(user.Id.toString())
//                }
//            }
//        }
//    }
}