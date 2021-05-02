package com.example.room.mvvm.view

import android.app.AlertDialog
import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.room.mvvm.R
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.utils.Util

class UserAdapter(
    private val userList: List<LoginTableModel>,
    private val cliclInterface: ClickInterface
) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //        private var isEditing = false
        var mypos: Int = -1
        val id: TextView = view.findViewById(R.id.id)
        val firstName: EditText = view.findViewById(R.id.firstName)
        val lastName: EditText = view.findViewById(R.id.lastName)
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
        holder.firstName.setText(user.Username)
        holder.lastName.setText(user.LastName)
        holder.pass.setText(user.Password)
        holder.age.setText(user.age.toString())

        holder.delete.apply {
            setOnClickListener {
                val dialog: Dialog = Util.getAlertDialog(context)

                dialog.apply {
                    findViewById<TextView>(R.id.alerttitle).text = "Delete User"
                    findViewById<TextView>(R.id.alertmessage).text =
                        "Are you sure,you want to delete the user?"
                    findViewById<TextView>(R.id.negativebtn).setOnClickListener {
                        dialog.cancel()
                    }
                    findViewById<TextView>(R.id.positivebtn).setOnClickListener {
                        cliclInterface.onDeleteItem(it, user.Id)
                        dialog.cancel()
                    }
                }
                dialog.show()
            }
        }

        holder.edit.apply {
            if (user.isEditing) {
                Log.d("TAG", "onBindViewHolder0: ")
                holder.firstName.isEnabled = true
                holder.firstName.background =
                    ContextCompat.getDrawable(context, R.drawable.mybackground)
                holder.lastName.isEnabled = true
                holder.lastName.background =
                    ContextCompat.getDrawable(context, R.drawable.mybackground)
                holder.pass.isEnabled = true
                holder.pass.background = ContextCompat.getDrawable(context, R.drawable.mybackground)
                holder.age.isEnabled = true
                holder.age.background = ContextCompat.getDrawable(context, R.drawable.mybackground)
                setImageResource(R.drawable.done_icon)
            } else {
                Log.d("TAG", "onBindViewHolder1: ")
                holder.firstName.isEnabled = false
                holder.firstName.background = null
                holder.lastName.isEnabled = false
                holder.lastName.background = null
                holder.pass.isEnabled = false
                holder.pass.background = null
                holder.age.isEnabled = false
                holder.age.background = null
                setImageResource(R.drawable.edit_icon)
            }
            setOnClickListener {
                if (user.isEditing) {
                    val dialog: Dialog = Util.getAlertDialog(context)

                    dialog.apply {
                        findViewById<TextView>(R.id.alerttitle).text = "Save User"
                        findViewById<TextView>(R.id.alertmessage).text =
                            "Do you want to save the changes?"
                        findViewById<TextView>(R.id.negativebtn).setOnClickListener {
                            user.isEditing = !user.isEditing
                            notifyDataSetChanged()
                            dialog.cancel()
                        }
                        findViewById<TextView>(R.id.positivebtn).setOnClickListener {
                            cliclInterface.onEditClick(
                                it,
                                user.Id,
                                holder.firstName.text.toString().trim(),
                                holder.lastName.text.toString().trim(),
                                holder.pass.text.toString().trim(),
                                holder.age.text.toString().trim().toInt(),

                                user.isEditing
                            )

                            dialog.cancel()

                        }
                    }
                    dialog.show()
                } else {
                    cliclInterface.onEditClick(
                        it,
                        user.Id, "null", "null", "null", 0,
                        user.isEditing
                    )
                }

            }
            Log.d("TAG", "ViewHolder: ${user.Id}")

        }
    }

    private fun sdf(dialog: AlertDialog.Builder) {
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    interface ClickInterface {
        fun onEditClick(
            view: View,
            id: Int,
            firstName: String,
            lastName: String,
            pass: String,
            age: Int,
            editing: Boolean
        )

        //        fun onEditClick(view: View, position: Int, boolean: Boolean)
        fun onDeleteItem(it: View, position: Int)
    }
}