package com.example.kosaa.ServiceActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kosaa.Adapter.chatActivityAdapter
import com.example.kosaa.Models.MessageModel
import com.example.kosaa.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class chatActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.hide()
        val workername = intent.getStringExtra("workerName")
        val receiverid = intent.getStringExtra("workerUid")
        val senderid = auth.uid

        // update data of reciver on toolbar
        workerNameChatActivity.setText(workername)

        backArrowBtn.setOnClickListener(View.OnClickListener {
            super.onBackPressed()
        })

        //chat room
        val senderroom = senderid + receiverid
        val receiverroom = receiverid + senderid

        //set adapter
        var messageDataset: ArrayList<MessageModel> = ArrayList()
        var msgAdapter = chatActivityAdapter(messageDataset,this)
        recyclerViewConversationActivity.adapter =msgAdapter

        //showing chats in recyclerview or  read msg from firebase(read database)
        database.getReference().child("CHATS").child(senderroom)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageDataset.clear()
                    for(snapshot1: DataSnapshot in snapshot.children)
                    {
                        var message: MessageModel? = snapshot1.getValue(MessageModel::class.java)
                        messageDataset.add(message!!)
                    }
                    msgAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        //send msg chats on firebase (write database)
        sendMsgBtn.setOnClickListener(View.OnClickListener {
            if(msgBoxET.length()!=0)
            {
                val message = msgBoxET.text.toString()
                msgBoxET.text.clear()

                var messagemodel:MessageModel = MessageModel(senderid.toString(),message)

                database.getReference().child("CHATS")
                    .child(senderroom).push()
                    .setValue(messagemodel).addOnSuccessListener(OnSuccessListener {

                        database.getReference().child("CHATS")
                            .child(receiverroom).push()
                            .setValue(messagemodel).addOnSuccessListener(OnSuccessListener {

                            })

                    })

            }
        })
    }
}