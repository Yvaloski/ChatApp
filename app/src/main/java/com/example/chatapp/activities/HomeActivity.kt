package com.example.chatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.adapters.FriendsRecyclerAdapter
import com.example.chatapp.models.Friend
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {


    lateinit var rvFriends: RecyclerView
    lateinit var fabChat: FloatingActionButton
    lateinit var friendsRecyclerAdapter: FriendsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rvFriends = findViewById(R.id.rvFriends)
        fabChat = findViewById(R.id.floatingActionButton)

        fabChat.setOnClickListener {

            Intent(this, UserSearchActivity::class.java).also {
                startActivity(it)
            }

        }
        val friends = mutableListOf<Friend>(

            Friend("Tony","WEWE ma caille"," ",420),
            Friend("Pedro salini","Salut ma caille"," ",420),
            Friend("Octo Hocho","Salut ma caille"," ",420)

        )

        friendsRecyclerAdapter = FriendsRecyclerAdapter()
        friendsRecyclerAdapter.items = friends
        rvFriends.apply { layoutManager = LinearLayoutManager(this@HomeActivity)
            rvFriends.adapter = friendsRecyclerAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId==R.id.itemSettings){

          Intent(this, SettingsActivity::class.java).also {
                startActivity(it)
            }


        }

        if (item.itemId==R.id.itemLogout){

            val auth = Firebase.auth
            auth.signOut()
            Intent(this, AuthentificationActivity::class.java).also {
                startActivity(it)
             }
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}