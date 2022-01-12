package com.example.orange_vente

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orange_vente.databinding.ActivityDashboardAdminBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardAdminBinding

    private lateinit var firebaseAuth: FirebaseAuth

    //private lateinit var
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()

        checkUser()

        //handle click
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        //set current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null)
        {
            startActivity(Intent(this,AccueilActivity::class.java))
            finish()
        }
        else
        {
            val email =firebaseUser.email

            binding.email.text = email
        }

    }
}