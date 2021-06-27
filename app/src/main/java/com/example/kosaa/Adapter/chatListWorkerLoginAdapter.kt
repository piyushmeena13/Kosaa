package com.example.kosaa.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kosaa.Models.UserModel
import com.example.kosaa.Models.workerModel
import com.example.kosaa.R
import com.example.kosaa.ServiceActivities.chatActivity

class chatListWorkerLoginAdapter(val dataset:ArrayList<UserModel>, val context: Context):
    RecyclerView.Adapter<chatListWorkerLoginAdapter.userViewHolder>() {

    class userViewHolder(view: View): RecyclerView.ViewHolder(view){
        val userName = view.findViewById<TextView>(R.id.workername)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item,parent,false)
        return userViewHolder(view)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {

        var user = dataset[position]
        holder.userName.text = user.username

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, chatActivity::class.java).apply {
                putExtra("workerName",user.username) //userName
                putExtra("workerUid",user.userid) //UserUid
            }
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}