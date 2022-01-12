package com.example.orange_vente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.orange_vente.databinding.ActivitySignup3Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login2.*

class Signup3Activity : AppCompatActivity() {
    private lateinit var binding:ActivitySignup3Binding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivitySignup3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.register.setOnClickListener {
            validateData()
        }
    }
    private var name = ""
    private var email = ""
    private var password = ""


    private fun validateData() {
        name = binding.name.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.password2.text.toString().trim()
        val cpassword = binding.password3.text.toString().trim()
    //validate Data
        if (name.isEmpty())
        {
            //empty name
            Toast.makeText(this,"Enter your name...",Toast.LENGTH_SHORT).show()

        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            //invalidate email
            Toast.makeText(this,"Invalide Email Pattern...",Toast.LENGTH_SHORT).show()

        }
        else if (password.isEmpty())
        {
            Toast.makeText(this,"Enter Password...",Toast.LENGTH_SHORT).show()

        }
        else if (cpassword.isEmpty())
        {
            Toast.makeText(this,"Confirm your Password...",Toast.LENGTH_SHORT).show()

        }
        else if (cpassword != password)
        {
            Toast.makeText(this,"Password doesn't match...",Toast.LENGTH_SHORT).show()

        }
        else{
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        progressDialog.setMessage("Creating Account")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //account created
                updateUserInfo()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed creating account du to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    private fun updateUserInfo() {
        progressDialog.setMessage("Saving user info...")

        //timestamp
        val timestamp = System.currentTimeMillis()

        //get current user
        val uid = firebaseAuth.uid

        //setup data
        val hashMap: HashMap<String, Any?> = HashMap()

        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["profileImage"] = ""
        hashMap["userType"] = "user"
        hashMap["timestamp"] = timestamp
        //set data to do
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {

                //user info saved
                progressDialog.dismiss()
                Toast.makeText(this,"Account created...",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@Signup3Activity,DashBoardActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                //failed adding datato db
                progressDialog.dismiss()
                Toast.makeText(this,"Failed saving user info du to ${e.message}",Toast.LENGTH_SHORT).show()


            }
    }


}
