package com.example.kosaa.RegistrationActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import com.example.kosaa.Models.workerModel
import com.example.kosaa.R
import com.example.kosaa.ServiceActivities.chatActivity
import com.example.kosaa.ServiceActivities.chatListWorkerLogin
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_worker_signup.*

class worker_signupActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    private lateinit var auth: FirebaseAuth
    var ans:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_signup)

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        val workerName = intent.getStringExtra("workerName")
        val workerEmail = intent.getStringExtra("workerEmail")

        createAccountBtn_id.setOnClickListener {
            if (validateUser()) {
                progressBar_id2.visibility = View.VISIBLE
                createAccountBtn_id.isEnabled = false
                createAccountBtn_id.isClickable = false
                createAccountBtn_id.alpha = 0.5f
                ans = onRadioButtonClicked()

                val workerid = auth.uid.toString()   //login id (unique id ) of current user
                val workerData = workerModel(workerName,workerEmail,workerid,ans,location_id.text.toString(),aboutBio_id.text.toString(),phone_id.text.toString())

                database.getReference().child("WORKERS")
                    .child(workerid)
                    .setValue(workerData)
                    .addOnSuccessListener(OnSuccessListener {

                        progressBar_id2.visibility = View.VISIBLE
                        createAccountBtn_id.isEnabled = false
                        createAccountBtn_id.isClickable = false
                        createAccountBtn_id.alpha = 0.5f

                        val intent = Intent(this, chatListWorkerLogin::class.java)
                        startActivity(intent)
                        finish()
                    })
            }
        }


    }

    private fun validateUser():Boolean {

        if(location_id.length()==0){
            location_id.setError("location is required")
            return false
        }

        if(phone_id.length()==0){
            phone_id.setError("mobile number is required")
            return false
        }
        if(phone_id.length()<10){
            phone_id.setError("number should be 10 digit")
            return false
        }
        if(aboutBio_id.length()<10){
            aboutBio_id.setError("Bio required(at least 10 words)")
            return false
        }

        return true
    }

    fun onRadioButtonClicked():String  {
        var temp: String? = null
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup_id)

        var i: Int = radioGroup.checkedRadioButtonId
        if(i == R.id.plumberRb_id)
            temp = "Plumber"
        else if(i == R.id.electricRb_id)
            temp = "Electrician"
        else if(i == R.id.cleanerRb_id)
            temp = "Cleaner"
        else if(i == R.id.mechanicRb_id)
            temp = "Mechanic"
        else
            temp = "Carpenter"

        Log.d("radioBtn func", temp.toString())
        return temp
    }
}