package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase.databinding.ActivityAddUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("MyUser")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddUser.setOnClickListener {
            addUserToDatabase()
        }
    }

    private fun addUserToDatabase() {
        val name: String = binding.etAddName.text.toString().trim()
        val ageText: String = binding.etAddAge.text.toString().trim()
        val email: String = binding.etAddEmail.text.toString().trim()

        // Input validation
        if (name.isEmpty() || ageText.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        val age: Int = try {
            ageText.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_SHORT).show()
            return
        }

        val userId: String? = myReference.push().key
        if (userId == null) {
            Toast.makeText(this, "Error generating user ID", Toast.LENGTH_SHORT).show()
            return
        }

        val user = Users(userId, name, age, email)

        myReference.child(userId).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
            }
    }
}
