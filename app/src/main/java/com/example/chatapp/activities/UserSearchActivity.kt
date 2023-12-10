package com.example.chatapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.adapters.UserRecyclerAdapter
import com.example.chatapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserSearchActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var currentUser: FirebaseUser? = null

    lateinit var rvUser: RecyclerView
    lateinit var editSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)

        auth = Firebase.auth
        db = Firebase.firestore
        currentUser = auth.currentUser

        editSearch = findViewById(R.id.editSearch)
        rvUser = findViewById(R.id.rvUser)
        val userRecyclerAdapter = UserRecyclerAdapter()
        rvUser.apply {

            layoutManager = LinearLayoutManager(this@UserSearchActivity)
            rvUser.adapter = userRecyclerAdapter
        }

        val users = mutableListOf<User>()

        db.collection("users")
            .whereNotEqualTo("email",currentUser?.email)
            .get()
            .addOnSuccessListener { result ->
            for (document in result) {
                val uuid = document.id
                val email = document.getString("email")
                val pseudo = document.getString("pseudo")
                users.add(User(uuid, email ?: "", pseudo ?: "", null))

            }
            userRecyclerAdapter.items = users


        }.addOnFailureListener { exeption ->
            Log.e("UserSearchActivity", "error getting users", exeption)
        }






        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                userRecyclerAdapter.filter.filter(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }


}