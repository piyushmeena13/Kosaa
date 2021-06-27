package com.example.kosaa.RegistrationActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.kosaa.Models.UserModel
import com.example.kosaa.ServiceActivities.HomeActivity
import com.example.kosaa.R
import com.example.kosaa.ServiceActivities.chatActivity
import com.example.kosaa.ServiceActivities.chatListWorkerLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        loginBtn_id.setOnClickListener(View.OnClickListener {
            if(validateUser()){
                progressBar_id1.visibility = View.VISIBLE
                loginBtn_id.isEnabled = false
                loginBtn_id.isClickable = false
                loginBtn_id.alpha = 0.5f
                auth.signInWithEmailAndPassword(email_id.text.toString(),password_id.text.toString())
                    .addOnCompleteListener(this){
                        if(it.isSuccessful){

                            checkingUserOrWorker()
                        }
                        else{
                            loginFailedTv_id.visibility = View.VISIBLE
                            progressBar_id1.visibility = View.GONE
                            loginBtn_id.isEnabled = true
                            loginBtn_id.isClickable = true
                            loginBtn_id.alpha = 1f
                        }
                    }
            }
        })

        signUpTv_id.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        })

    }

    private fun checkingUserOrWorker() {

        database.getReference().child("USERS").addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for(snapshot1: DataSnapshot in snapshot.children)
                {
                    var user : UserModel? = snapshot1.getValue(UserModel::class.java)
                    Log.d("LoginActivity",user.toString())
                    Log.d("LoginActivity1",auth.uid.toString())
                    if(user?.userid == auth.uid.toString())
                    {
                        Log.d("LoginActivity2",user.toString())
                        loginUser()
                        Log.d("LoginActivity2",user.toString())
                        break
                    }
                    else
                    {
                        loginworker()
                        break
                    }

                    Log.d("LoginActivity3",user.toString())

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun validateUser():Boolean {
        if(email_id.length()==0){
            email_id.setError("Email is required")
            return false
        }
        if(password_id.length()==0){
            password_id.setError("Password is required")
            return false
        }

        return true
    }

    private fun loginworker() {
        loginFailedTv_id.visibility = View.GONE
        progressBar_id1.visibility = View.GONE
        startActivity(Intent(this, chatListWorkerLogin::class.java))
        finishAffinity()
    }

    private fun loginUser() {
        loginFailedTv_id.visibility = View.GONE
        progressBar_id1.visibility = View.GONE
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }

}