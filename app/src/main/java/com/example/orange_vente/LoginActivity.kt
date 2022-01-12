package com.example.orange_vente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.orange_vente.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding :ActivityLoginBinding


    //viewBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //progress Dialog
    private lateinit var progressDialog: ProgressDialog
    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionBar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        //configure progress Dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click begin login
        binding.loginBtn.setOnClickListener {
            //before logging
            validateData()
        }

    }

    private fun validateData() {
        //get Data
        email= binding.loginEmail.text.toString().trim()
        password =binding.password1.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalide Email

            binding.loginEmail.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password))
        {
            //no password enter
            binding.password1.error = "Please enter password"
        }
        else{
            //data is validated
            firebaseLogin()
        }

    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //loggin success
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"LoggedIn as  $email",Toast.LENGTH_SHORT).show()
                //open profile
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                //loggin failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user is already logged in
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser !=null)
        {
            //user is logged iIn
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()
        }
    }
}