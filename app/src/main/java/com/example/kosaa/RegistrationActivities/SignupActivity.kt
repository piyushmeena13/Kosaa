package com.example.kosaa.RegistrationActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import com.example.kosaa.ServiceActivities.HomeActivity
import com.example.kosaa.Models.UserModel
import com.example.kosaa.Models.workerModel
import com.example.kosaa.R
import com.example.kosaa.ServiceActivities.chatActivity
import com.example.kosaa.ServiceActivities.chatListWorkerLogin
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.email_id
import kotlinx.android.synthetic.main.activity_login.password_id
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    private lateinit var auth: FirebaseAuth
    var ans:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()


        signUpBtn_id.setOnClickListener(View.OnClickListener {
            if(validateUser()){
                progressBar_id.visibility = View.VISIBLE
                signUpBtn_id.isEnabled = false
                signUpBtn_id.isClickable = false
                signUpBtn_id.alpha = 0.5f
                ans = onRadioButtonClicked()

                auth.createUserWithEmailAndPassword(email_id.text.toString(),password_id.text.toString())
                    .addOnCompleteListener(this){
                        if (it.isSuccessful){
                            progressBar_id.visibility = View.GONE

                            if(ans == 1) //user
                            {
                                Toast.makeText(baseContext, "user ", Toast.LENGTH_SHORT).show()
                                user_Data_Upload_On_FireBase()
                            }
                            else if(ans == 2)    // worker
                            {
                                Toast.makeText(baseContext, "Worker ", Toast.LENGTH_SHORT).show()
                                var intent = Intent(this, worker_signupActivity::class.java).apply {
                                    putExtra("workerName",name_id.text.toString())
                                    putExtra("workerEmail",email_id.text.toString())
                                }
                                startActivity(intent)
                                finish()
                            }
                        }
                        else{
                            progressBar_id.visibility = View.GONE
                            signUpBtn_id.isEnabled = true
                            signUpBtn_id.isClickable = true
                            signUpBtn_id.alpha = 1f
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        })

        loginTv_id.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
    }

    private fun user_Data_Upload_On_FireBase() {

        val Uid = auth.uid.toString()   //login id (unique id ) of current user
        val userData = UserModel(Uid,email_id.text.toString(),name_id.text.toString(),"user" )

        database.getReference().child("USERS")
            .child(Uid)
            .setValue(userData)
            .addOnSuccessListener(OnSuccessListener {

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            })
    }

    private fun validateUser():Boolean {

        if(name_id.length()==0){
            name_id.setError("Name is required")
            return false
        }
        if(name_id.length()<5){
            name_id.setError("Name should be minimum 5 length")
            return false
        }
        if(email_id.length()==0){
            email_id.setError("Email is required")
            return false
        }
        if(password_id.length()==0){
            password_id.setError("Password is required")
            return false
        }
        if(password_id.length()<6){
            password_id.setError("Password is weak")
            return false
        }
        if(password_id.text.toString()!=confirmPassword_id.text.toString()){
            confirmPassword_id.setError("Password Mismatch!")
            return false
        }

        return true
    }

    fun onRadioButtonClicked():Int
    {
        var temp:Int = 0
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup_id)

        var i: Int = radioGroup.checkedRadioButtonId
        if(i == R.id.userRb_id)
        {
            temp =1
        }
        else
        {
            temp =2
        }

        Log.d("radioBtn func", temp.toString())
        return temp
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser!=null)
        {
            var  temp = 0;

            database.getReference().child("USERS").addValueEventListener(object :
                ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    for(snapshot1: DataSnapshot in snapshot.children)
                    {
                        var user : UserModel? = snapshot1.getValue(UserModel::class.java)
                        Log.d("SignUpActivity",user.toString())
                        Log.d("SignUpActivity1",auth.uid.toString())
                        if(user?.userid == auth.uid.toString())
                        {
                            Log.d("SignUpActivity2",user.toString())
                            loginUser()
                            Log.d("SignUpActivity2",user.toString())
                            break
                        }
                        else
                        {
                            workerUser()
                            break
                        }

                        Log.d("SignUpActivity3",user.toString())

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

            Log.d("SignUpActivity4",temp.toString())

        }
    }

    private fun workerUser() {
        startActivity(Intent(this, chatListWorkerLogin::class.java))
        finishAffinity()
    }

    private fun loginUser() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }
}