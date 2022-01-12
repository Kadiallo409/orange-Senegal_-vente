package com.example.orange_vente

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orange_vente.databinding.ActivityDashBoardBinding
import com.google.firebase.auth.FirebaseAuth

class DashBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBoardBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
           startActivity(Intent(this,AccueilActivity::class.java))
            finish()
        }
    }

    private fun checkUser() {
       val firebaseUser =firebaseAuth.currentUser
        if (firebaseUser ==null)
        {
            binding.email.text = "Not Logged In"
        }
        else
        {
            val email = firebaseUser.email
            binding.email.text = email
        }

    }
}