package com.example.orange_vente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.orange_vente.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    //viewbinding
    private lateinit var binding: ActivityProfileBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog:ProgressDialog


    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //configure progress Dialog


        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click log out
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null)
        {
            //get user
            val email = firebaseUser.email
            //set to text view
            binding.emailtv.text = email

        }
        else
        {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}