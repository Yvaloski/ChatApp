package com.example.chatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.chatapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class AuthentificationActivity : AppCompatActivity() {
    lateinit var tv_Register: TextView
    lateinit var textInputLayoutEmail: TextInputLayout
    lateinit var textInputLayoutPassword: TextInputLayout
    lateinit var btn_connect:MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)

        tv_Register = findViewById(R.id.tv_register)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword)
        btn_connect=findViewById(R.id.btn_connect)


        tv_Register.setOnClickListener{ Intent(this,RegisterActivity::class.java).also {
            startActivity(it)
        }}

        btn_connect.setOnClickListener{

            textInputLayoutPassword.isErrorEnabled=false
            textInputLayoutEmail.isErrorEnabled=false
            val email = textInputLayoutEmail.editText?.text.toString()
            val password = textInputLayoutPassword.editText?.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                if (email.isEmpty()){
                    textInputLayoutEmail.error ="email Required"
                    textInputLayoutEmail.isErrorEnabled=true
                }
                if (password.isEmpty()) {

                    textInputLayoutPassword.error ="Password required"
                    textInputLayoutPassword.isErrorEnabled=true
                }
                } else {
                    signIn(email,password)
                }

        }
    }

    fun signIn(email: String, password: String) {

        Log.d("signIn","Sign in user")

        if (email == "lolo@gmail.com" && password=="123") {

            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }else{

            textInputLayoutEmail.error="Authentification failed"
            textInputLayoutPassword.error="Authentification failed"
            textInputLayoutEmail.isErrorEnabled=true
            textInputLayoutPassword.isErrorEnabled=true
        }
    }
}