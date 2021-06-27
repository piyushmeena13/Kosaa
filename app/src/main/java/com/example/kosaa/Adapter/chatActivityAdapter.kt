package com.example.kosaa.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kosaa.Models.MessageModel
import com.example.kosaa.R
import com.google.firebase.auth.FirebaseAuth

class chatActivityAdapter(val messagesdata:ArrayList<MessageModel>, val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT:Int = 1
    val ITEM_RECEIVE:Int = 2

    class senderViewHolder(view: View): RecyclerView.ViewHolder(view){
        val senderMessageTv = view.findViewById<TextView>(R.id.sendMessageTv)
    }
    class receiverViewHolder(view: View): RecyclerView.ViewHolder(view){
        val receiverMessageTv = view.findViewById<TextView>(R.id.receiveMessageTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType ==ITEM_SENT){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_send,parent,false)
            return senderViewHolder(view)
        }
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.receive_item,parent,false)
            return receiverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var message:MessageModel = messagesdata[position]
        if(holder.javaClass ==senderViewHolder::class.java){
            var viewHolder:senderViewHolder = holder as senderViewHolder
            viewHolder.senderMessageTv.text =message.userMessages.toString()
        }else{
            var viewHolder:receiverViewHolder = holder as receiverViewHolder
            viewHolder.receiverMessageTv.text = message.userMessages.toString()
        }
    }

    override fun getItemCount(): Int {
        return  messagesdata.size
    }

    override fun getItemViewType(position: Int): Int {

        val message:MessageModel = messagesdata[position]

        if(FirebaseAuth.getInstance().uid.equals(message.senderID))
            return ITEM_SENT
        else
            return ITEM_RECEIVE
    }
}