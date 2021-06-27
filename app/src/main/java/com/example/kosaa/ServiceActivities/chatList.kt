package com.example.kosaa.ServiceActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kosaa.Adapter.chatListAdapter
import com.example.kosaa.Models.workerModel
import com.example.kosaa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat_list.*
import kotlinx.android.synthetic.main.activity_service_provide_info.*

class chatList : AppCompatActivity() {

    val dataset:ArrayList<workerModel> = ArrayList()
    var database = FirebaseDatabase.getInstance()
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        supportActionBar?.hide()

        backBtnChatList_id.setOnClickListener {
            super.onBackPressed()
        }

        var usersAdapter = chatListAdapter(dataset,this)
        recyclerviewChatList.adapter=usersAdapter

        database.getReference().child("WORKERS").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataset.clear()
                for(snapshot1: DataSnapshot in snapshot.children)
                {
                    var worker: workerModel? = snapshot1.getValue(workerModel::class.java)
                    dataset.add(worker!!)

                }
                usersAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}