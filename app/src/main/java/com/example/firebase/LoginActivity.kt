package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.firebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding : ActivityLoginBinding

    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.btnSignIn.setOnClickListener {

            val userEmail = loginBinding.editTextEmailSignIn.text.toString()
            val userPassword = loginBinding.editTextPasswordSignIn.text.toString()

            signInWithFirebase(userEmail , userPassword)

        }
        loginBinding.btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity , SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBinding.btnForgotPass.setOnClickListener {
            val intent = Intent(this@LoginActivity , ForgetActivity::class.java)
            startActivity(intent)
        }
        loginBinding.btnPhoneNum.setOnClickListener {
            val intent = Intent(this@LoginActivity , PhoneActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signInWithFirebase(userEmail : String , userPassword : String){

        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(applicationContext , " Signed In Successfully" , Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity , MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {

                    Toast.makeText(applicationContext , task.exception?.toString() , Toast.LENGTH_SHORT).show()

                }
            }

    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser

        if (user!=null){
            val intent = Intent(this@LoginActivity , MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}