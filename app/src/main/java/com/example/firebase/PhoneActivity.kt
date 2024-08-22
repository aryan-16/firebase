package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase.databinding.ActivityPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneActivity : AppCompatActivity() {
    lateinit var phoneBinding : ActivityPhoneBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var mCallbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var verificationCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phoneBinding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(phoneBinding.root)

        phoneBinding.buttonSendVerify.setOnClickListener {
            val userphone = phoneBinding.editTextPhone.text.toString()
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(userphone)
                .setTimeout(30L , TimeUnit.SECONDS)
                .setActivity(this@PhoneActivity)
                .setCallbacks(mCallbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)



        }
        phoneBinding.buttonotp.setOnClickListener {

            signInWithSMSCode()

        }

        mCallbacks  = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)

                verificationCode = p0
            }

        }
    }

    fun signInWithSMSCode(){
        val userCode = phoneBinding.editTextCode.text.toString()

        val credential = PhoneAuthProvider.getCredential(verificationCode , userCode)
        SigninWithAuthCredential(credential)
    }

    fun SigninWithAuthCredential (credential: PhoneAuthCredential){
        auth.signInWithCredential(credential).addOnCompleteListener {   task->

            if(task.isSuccessful){
                val intent = Intent(this@PhoneActivity , MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                Toast.makeText(applicationContext , "Wrong Code Entered" , Toast.LENGTH_SHORT).show()

            }

        }
    }
}