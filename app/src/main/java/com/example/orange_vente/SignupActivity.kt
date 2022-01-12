package com.example.orange_vente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.orange_vente.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog


    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var passwords = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
        actionBar= supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure progress Dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click begin signup
        binding.button.setOnClickListener {

            //validate data
            validateData()
        }

        }

    private fun validateData() {
        //get Data
        email= binding.Email.text.toString().trim()
        passwords =binding.password.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalide Email

            binding.Email.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(passwords))
        {
            //no password enter
            binding.password.error = "Please enter password"
        }
        else if(passwords.length <6){
            binding.password.error = "password must at least 6 chracters long"


        }
        else {
            firebseSignup()


    }


}

    private fun firebseSignup() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, passwords)
            .addOnSuccessListener {
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with email ${email}", Toast.LENGTH_SHORT).show()
                //open new fenetre
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this,"Signup failed due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()//go back to previous activity
        return super.onSupportNavigateUp()
    }
}