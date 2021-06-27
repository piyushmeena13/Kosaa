package com.example.kosaa.Adapter

import android.content.Context
import android.content.Intent
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kosaa.Models.workerModel
import com.example.kosaa.R
import com.example.kosaa.ServiceActivities.serviceProvideInfoActivity
import kotlinx.android.synthetic.main.service_provider_list_item.view.*

class serviceProviderListAdapter(val dataset: ArrayList<workerModel>,val context: Context):RecyclerView.Adapter<serviceProviderListAdapter.workerViewHolder>(){

    class workerViewHolder(view: View):RecyclerView.ViewHolder(view){
        val workerName = view.findViewById<TextView>(R.id.workerNameList_id)
        val workerProfession = view.findViewById<TextView>(R.id.professionList_id)
        val workerRating = view.findViewById<TextView>(R.id.workerRatingList_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): workerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.service_provider_list_item,parent,false)
        return workerViewHolder(view)
    }

    override fun onBindViewHolder(holder: workerViewHolder, position: Int) {
        var worker = dataset[position]

        holder.workerName.text = worker.workername
        holder.workerProfession.text = worker.profession

        holder.itemView.setOnClickListener(View.OnClickListener {

            Toast.makeText(context, "item clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(context,serviceProvideInfoActivity::class.java).apply {
                putExtra("workerName",worker.workername)
                putExtra("workerProfession",worker.profession)
                putExtra("workerBio",worker.workerBio)
                putExtra("workerNumber",worker.workerNumber)
                putExtra("workerUid",worker.workerid)
            }
            context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}