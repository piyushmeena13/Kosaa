package com.example.kosaa.ServiceActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kosaa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView.selectedItemId = R.id.homeBtn_Id
        supportActionBar?.hide()    // hide the toolbar

//        val Uid = auth.uid.toString()   //login id (unique id ) of current user
//        val userEmail = intent.getStringExtra("Email")
//        val userName = intent.getStringExtra("Name")
//        val userProfesion = intent.getStringExtra("profesion")

        bottomNavigationView.setOnNavigationItemSelectedListener{

            when (it.itemId) {
                R.id.chatBtn_Id -> {
                    Toast.makeText(baseContext,"chatBtn clicked",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,chatList::class.java))

                    true
                }
                R.id.homeBtn_Id -> {

                    true
                }
                R.id.userProfileBtn_Id -> {
                    true
                }
                else -> false
            }
        }

        plumberBtn_id.setOnClickListener {
            var intent = Intent(this, serviceProviderListActivity::class.java).apply {
                putExtra("serviceName","Plumber")
            }
            startActivity(intent)
        }

        electricalBtn_id.setOnClickListener {

        }

        cleaningBtn_id.setOnClickListener {

        }

        autoServiceBtn_id.setOnClickListener {

        }

        mechanicBtn_id.setOnClickListener {

        }

        carpenterBtn_id.setOnClickListener {

        }

        painterBtn_id.setOnClickListener {

        }

        cheifBtn_id.setOnClickListener {

        }






    }

}