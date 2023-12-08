package com.example.chatapp.activities

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.adapters.ChatRecyclerAdapter
import com.example.chatapp.models.Message
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ChatActivity : AppCompatActivity() {

    lateinit var fabSendMessage: FloatingActionButton
    lateinit var editMessage: EditText
    lateinit var rvChatList: RecyclerView
    lateinit var chatRecyclerAdapter: ChatRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fabSendMessage = findViewById(R.id.fabSendMessage)
        editMessage = findViewById(R.id.editMessage)
        rvChatList = findViewById(R.id.rvChatList)
        val name = intent.getStringExtra("friend")
        supportActionBar?.title = name ?: "ChatApp"
        chatRecyclerAdapter = ChatRecyclerAdapter()




        val messages = mutableListOf<Message>(

            Message(
                sender = "Yval",
                receiver = "Tony",
                text = "Salut",
                timestamp = 123456789,
                isReceived = false
            ),
            Message(
                sender = "Yval",
                receiver = "Tony",
                text = "Salut mrv",
                timestamp = 123456789,
                isReceived = false
            ),
            Message(
                sender = "Tony",
                receiver = "Yval",
                text = "cv",
                timestamp = 123456789,
                isReceived = true
            ),
            Message(
                sender = "Tony",
                receiver = "Yval",
                text = "ou quoi",
                timestamp = 123456789,
                isReceived = true
            ),
            Message(
                sender = "Yval",
                receiver = "Tony",
                text = "Salut",
                timestamp = 123456789,
                isReceived = false
            ),


            )

        fabSendMessage.setOnClickListener {
            //envoyer message
            val message = editMessage.text.toString()
            if (message.isNotEmpty()) {
                messages.add(
                    Message(
                        sender = "Yval",
                        receiver = "Tony",
                        text = message,
                        timestamp = System.currentTimeMillis(),
                        isReceived = false
                    ),
                )
                chatRecyclerAdapter.notifyDataSetChanged()
                editMessage.setText("")

                //hide keyboard
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(editMessage.windowToken, 0)
            }
        }


        rvChatList.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatRecyclerAdapter
        }
        chatRecyclerAdapter.items = messages


        }

}