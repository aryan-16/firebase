package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.firebase.databinding.ActivityForgetBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetActivity : AppCompatActivity() {
    lateinit var forgetBinding : ActivityForgetBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgetBinding = ActivityForgetBinding.inflate(layoutInflater)
        setContentView(forgetBinding.root)

        forgetBinding.buttonReset.setOnClickListener {
            val email = forgetBinding.etEmail.text.toString()

            auth.sendPasswordResetEmail(email).addOnCompleteListener {task->

                if (task.isSuccessful){
                    Toast.makeText(applicationContext , "Mail sent to you" , Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }

            }
        }
    }
}