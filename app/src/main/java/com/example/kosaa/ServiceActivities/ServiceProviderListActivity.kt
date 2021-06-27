package com.example.kosaa.ServiceActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.kosaa.Adapter.serviceProviderListAdapter
import com.example.kosaa.Models.workerModel
import com.example.kosaa.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_service_provider_list.*

class serviceProviderListActivity : AppCompatActivity() {

    val dataset:ArrayList<workerModel> = ArrayList()
    var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_provider_list)

        supportActionBar?.hide()
        val Tittle = intent.getStringExtra("serviceName")
        title_id.setText(Tittle)

        backArrowBtnList.setOnClickListener(View.OnClickListener {
            super.onBackPressed()
        })

//        dataset.add(workerModel("akhil","@","1","Plumber","Kurukshetra","dont know","123"))
//        dataset.add(workerModel("varun","@","1","Plumber","Kurukshetra","dont know","123"))
//        dataset.add(workerModel("ritik","@","1","Plumber","Kurukshetra","dont know","123"))

        var workerAdapter = serviceProviderListAdapter(dataset,this)
        recyclerviewWorkerList_id.adapter = workerAdapter

        database.getReference().child("WORKERS").addValueEventListener(object :ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                dataset.clear()
                for(snapshot1:DataSnapshot in snapshot.children)
                {
                    Log.d("serviceProviderList","database function 2")
                    var worker : workerModel? = snapshot1.getValue(workerModel::class.java)
                    if(worker?.profession == Tittle)
                        dataset.add(worker!!)
                }

                workerAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
}