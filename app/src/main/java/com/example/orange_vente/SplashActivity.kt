package com.example.orange_vente


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SplashActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    //private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        firebaseAuth = FirebaseAuth.getInstance()



       Handler().postDelayed(Runnable {
           checkUser()
       },2000)

    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null)
        {
            startActivity(Intent(this,AccueilActivity::class.java))
            finish()
        }
        else
        {
            val ref = FirebaseDatabase.getInstance().getReference("Users")
                ref.child(firebaseUser.uid)
                    .addListenerForSingleValueEvent(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {

                            //get user type
                            var userType = snapshot.child("userType").value
                            if (userType =="admin")
                            {
                                startActivity(Intent(this@SplashActivity,DashboardAdminActivity::class.java))
                                finish()
                            }
                            else if (userType == "user")
                            //simple user
                                startActivity(Intent(this@SplashActivity,DashBoardActivity::class.java))
                            finish()

                        }
                        override fun onCancelled(error: DatabaseError) {


                        }


                    })
            }
        }
    }
