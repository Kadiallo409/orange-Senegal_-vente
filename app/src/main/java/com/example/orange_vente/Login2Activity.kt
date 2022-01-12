package com.example.orange_vente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.orange_vente.databinding.ActivityLogin2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLogin2Binding


    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click

        binding.noAccount.setOnClickListener {
            startActivity(Intent(this,Signup3Activity::class.java))

        }
        binding.login.setOnClickListener {
            //input data
            //validate data
            //login  firebase auth
            //check user type
            //if user move to user dashboard
            //if admin move admin dashboard
            validateData()
        }


    }
    private var email = ""
    private var password = ""

    private fun validateData() {
        email = binding.email.text.toString().trim()
        password = binding.password2.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(this,"Invalid Email format...", Toast.LENGTH_SHORT).show()

        }
        else if (password.isEmpty())
        {
            Toast.makeText(this,"Enter Password...",Toast.LENGTH_SHORT).show()

        }
        else{
            loginUser()
        }
    }
    private fun loginUser(){
        //login to firebase auth
         progressDialog.setMessage("Logging")
        progressDialog.show()

        //show progress
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                checkUser()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    private fun checkUser() {
        progressDialog.setMessage("checking User...")
        val firebaseUser = firebaseAuth.currentUser

        val ref = FirebaseDatabase.getInstance().getReference("Users")
            ref.child(firebaseUser!!.uid)
                .addListenerForSingleValueEvent(object: ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {
                        progressDialog.dismiss()
                        //get user type
                        var userType = snapshot.child("userType").value
                        //if (userType =="admin")
                        //{
                            //startActivity(Intent(this@Login2Activity,DashboardAdminActivity::class.java))
                            //finish()
                        //}
                        //else if (userType == "user")
                            //simple user
                                startActivity(Intent(this@Login2Activity,CartActivity::class.java))
                               finish()

                    }
                    override fun onCancelled(error: DatabaseError) {


                    }


                })
        }

}

