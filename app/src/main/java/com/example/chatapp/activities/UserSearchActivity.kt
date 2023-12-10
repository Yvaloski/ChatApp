package com.example.chatapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.adapters.UserRecyclerAdapter
import com.example.chatapp.models.User

class UserSearchActivity : AppCompatActivity() {


    lateinit var rvUser: RecyclerView
    lateinit var editSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)

        editSearch = findViewById(R.id.editSearch)
        rvUser = findViewById(R.id.rvUser)


        val users = mutableListOf<User>(

            User("antoine@gmail.com", "Tony", ""),
            User("jo@gmail.com", "Octo Hocho", ""),
            User("Pedro@gmail.com", "Pedro salini", ""),
            User("Lilj@gmail.com", "Lil John", "")

        )

        val userRecyclerAdapter = UserRecyclerAdapter()
        rvUser.apply {

            layoutManager = LinearLayoutManager(this@UserSearchActivity)
          rvUser.adapter= userRecyclerAdapter
        }

        userRecyclerAdapter.items = users


        editSearch.addTextChangedListener(object : TextWatcher{
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