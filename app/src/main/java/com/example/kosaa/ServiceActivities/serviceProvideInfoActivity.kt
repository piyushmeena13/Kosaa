package com.example.kosaa.ServiceActivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kosaa.R
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_service_provide_info.*

class serviceProvideInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_provide_info)

        supportActionBar?.hide()

        var workerName = intent.getStringExtra("workerName")
        var workerProfession = intent.getStringExtra("workerProfession")
        var workerBio = intent.getStringExtra("workerBio")
        var workerNumber = "+91"+intent.getStringExtra("workerNumber")
        var workerUid = intent.getStringExtra("workerUid")

        workerNameinfo_id.setText(workerName)
        professioninfo_id.setText(workerProfession)
        aboutBioinfo_id.setText(workerBio)

        backBtninfo_id.setOnClickListener(View.OnClickListener {
            super.onBackPressed()
        })

        callBtn_id.setOnClickListener {
            Toast.makeText(baseContext,"callBtn clicked",Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+Uri.encode(workerNumber)))
            startActivity(intent)
        }

        chatBtn_id.setOnClickListener {

            Toast.makeText(baseContext,"chatBtn clicked",Toast.LENGTH_SHORT).show()
            var intent = (Intent(this,chatActivity::class.java).apply
            {
                putExtra("workerName",workerName)
                putExtra("workerUid",workerUid)
            })

            startActivity(intent)
        }
    }
}