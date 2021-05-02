package com.example.room.mvvm.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.room.mvvm.R
import com.example.room.mvvm.model.LoginTableModel

class UserAdapter(
    private val userList: List<LoginTableModel>,
    private val cliclInterface: ClickInterface
) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //        private var isEditing = false
        var mypos: Int = -1
        val id: TextView = view.findViewById(R.id.id)
        val name: EditText = view.findViewById(R.id.name)
        val pass: EditText = view.findViewById(R.id.password)
        val age: EditText = view.findViewById(R.id.age)
        val edit: ImageView = view.findViewById(R.id.edit)
        val delete: ImageView = view.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.login_list, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = userList.get(position)
        holder.mypos = user.Id!!
        holder.id.text = user.Id.toString()
        holder.name.setText(user.Username)
        holder.pass.setText(user.Password)
        holder.age.setText(user.age.toString())

        holder.delete.apply {
            setOnClickListener { cliclInterface.onDeleteItem(it, user.Id) }
        }

        holder.edit.apply {
            if (user.isEditing) {
                Log.d("TAG", "onBindViewHolder0: ")
                holder.name.isEnabled = true
                holder.pass.isEnabled = true
                holder.age.isEnabled = true
                setImageResource(R.drawable.done_icon)
            } else {
                Log.d("TAG", "onBindViewHolder1: ")
                holder.name.isEnabled = false
                holder.pass.isEnabled = false
                holder.age.isEnabled = false
                setImageResource(R.drawable.edit_icon)
            }
            setOnClickListener {
                if (user.isEditing) {
                    cliclInterface.onEditClick(
                        it,
                        user.Id,
                        holder.name.text.toString().trim(),
                        holder.pass.text.toString().trim(),
                        holder.age.text.toString().trim().toInt(),

                        user.isEditing
                    )
                } else {
                    cliclInterface.onEditClick(
                        it,
                        user.Id, "null", "null",0,
                        user.isEditing
                    )
                }

            }
            Log.d("TAG", "ViewHolder: ${user.Id}")

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    interface ClickInterface {
        fun onEditClick(view: View, id: Int, name: String, pass: String, age:Int,editing: Boolean)

        //        fun onEditClick(view: View, position: Int, boolean: Boolean)
        fun onDeleteItem(it: View, position: Int)
    }
}