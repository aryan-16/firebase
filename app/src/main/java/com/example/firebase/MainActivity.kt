package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.databinding.ActivityAddUserBinding
import com.example.firebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myrefrence : DatabaseReference = database.reference.child("MyUser")

    val userList = ArrayList<Users>()
    lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrieveDataFRomDatabase()

        binding.floatingActionButton2.setOnClickListener{
            val intent = Intent(this , AddUserActivity::class.java)
            startActivity(intent)



        }
    }

    fun retrieveDataFRomDatabase(){
        myrefrence.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
               for(eachUser in snapshot.children){

                   val user = eachUser.getValue(Users::class.java)
                   if(user!= null){
                       println("userId : ${user.userId}")
                       println("userName : ${user.userName}")
                       println("userAge : ${user.userAge}")
                       println("****************")

                       userList.add(user)
                   }
                   userAdapter = UserAdapter(this@MainActivity , userList)

                   binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                   binding.recyclerView.adapter = userAdapter
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}