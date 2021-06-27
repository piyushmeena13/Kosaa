package com.example.kosaa.ServiceActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kosaa.Adapter.chatListAdapter
import com.example.kosaa.Adapter.chatListWorkerLoginAdapter
import com.example.kosaa.Models.UserModel
import com.example.kosaa.Models.workerModel
import com.example.kosaa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat_list.*

class chatListWorkerLogin : AppCompatActivity() {

    val dataset:ArrayList<UserModel> = ArrayList()
    var database = FirebaseDatabase.getInstance()
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list_worker_login)

        supportActionBar?.hide()

        var usersAdapter = chatListWorkerLoginAdapter(dataset,this)
        recyclerviewChatList.adapter=usersAdapter

        database.getReference().child("USERS").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataset.clear()
                for(snapshot1: DataSnapshot in snapshot.children)
                {
                    var user: UserModel? = snapshot1.getValue(UserModel::class.java)
                    dataset.add(user!!)

                }
                usersAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}