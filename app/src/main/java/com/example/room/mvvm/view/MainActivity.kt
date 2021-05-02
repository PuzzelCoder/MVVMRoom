package com.example.room.mvvm.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.mvvm.R
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.utils.Util.Companion.hideKeyboard
import com.example.room.mvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.details_form.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserAdapter.ClickInterface {

    lateinit var loginViewModel: LoginViewModel
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
        recyclerView.adapter = userAdapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )
        context = this@MainActivity
        detailsrl.visibility = VISIBLE
        addrl.visibility = GONE
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setObservers()
    }


    fun setObservers() {
        loginViewModel.getAllDetails()!!.observe(this, Observer {
            if (it == null || (it as List<LoginTableModel>).size == 0) {
                val s = (it as List<LoginTableModel>).size
                Log.d("TAG", "onCreate3: $s")
                nousers.visibility = VISIBLE
                recyclerView.visibility = View.GONE
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
                nousers.visibility = GONE
                recyclerView.visibility = VISIBLE
            }
        })
    }

    override fun onEditClick(
        view: View,
        id: Int,
        firstName: String,
        lastName: String,
        pass: String,
        age: Int,
        editing: Boolean
    ) {
        hideKeyboard(view)
        Log.d("TAG", "Edit Clicked at position $id: ")
        val user = LoginTableModel(id, "", "","", 0)
        val index = usersList.indexOf(user)
        if (index < 0) {
            Log.d("TAG", "onEditClick: User not found")
        } else {
            usersList.get(index).isEditing = !editing
            userAdapter.notifyDataSetChanged()

            if (editing) {
                loginViewModel.updateData(id,firstName,lastName,pass, age)
                Log.d("TAG", "onDoneClick: ")
            }
        }
    }

    override fun onDeleteItem(it: View, id: Int) {
        Log.d("TAG", "Delete Clicked at position $id: ")
        val result: Int? = loginViewModel.deleteLoginUser(id)
        if (result!! > 0) {
            Toast.makeText(context, "User deleted succssfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun showForm(view: View) {
        detailsrl.visibility = GONE
        addrl.visibility = VISIBLE
        hideKeyboard(view)
    }

    fun addUser(view: View) {
        val strUsername = txtUsername.text.toString().trim()
        val strLastname = txtLastname.text.toString().trim()
        val strPassword = txtPassword.text.toString().trim()
        val strAge = txtage.text.toString().trim()

        if (strUsername.isEmpty()) {
            txtUsername.error = "Enter the Firstname"
        } else if (strLastname.isEmpty()) {
            txtLastname.error = "Enter the LastName"
        }  else if (strPassword.isEmpty()) {
            txtPassword.error = "Enter the password"
        } else if (strAge.isEmpty() || strAge.toInt() <= 0) {
            txtage.error = "Enter  correct age"
        } else {
            loginViewModel.insertData(strUsername,strLastname, strPassword, strAge.toInt())

            Toast.makeText(this@MainActivity, "Inserted Successfully", Toast.LENGTH_SHORT).show()
            txtUsername.text = null
            txtPassword.text = null
            txtage.text = null
            detailsrl.visibility = VISIBLE
            addrl.visibility = GONE
        }
        hideKeyboard(view)
    }

    override fun onBackPressed() {
        if (addrl.visibility == VISIBLE) {
            detailsrl.visibility = VISIBLE
            addrl.visibility = GONE
        } else {
            super.onBackPressed()
        }
    }

}