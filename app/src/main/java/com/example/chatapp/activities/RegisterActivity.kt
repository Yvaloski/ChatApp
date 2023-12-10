package com.example.chatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    lateinit var layoutTextInputEmail: TextInputLayout
    lateinit var layoutTextInputPassword: TextInputLayout

    lateinit var layoutTextInputPseudo: TextInputLayout

    lateinit var layoutTextInputConfirmPassword: TextInputLayout
    lateinit var buttunRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        layoutTextInputEmail = findViewById(R.id.textInputLayoutEmail)
        layoutTextInputPseudo = findViewById(R.id.textInputLayoutpseudo)
        layoutTextInputPassword = findViewById(R.id.textInputLayoutPassword)
        layoutTextInputConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword)

        buttunRegister = findViewById(R.id.buttonRegister)


        buttunRegister.setOnClickListener {

            initErrors()

            val email = layoutTextInputEmail.editText?.text.toString()
            val pseudo = layoutTextInputPseudo.editText?.text.toString()
            val password = layoutTextInputPassword.editText?.text.toString()
            val confirmPassword = layoutTextInputConfirmPassword.editText?.text.toString()


            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || pseudo.isEmpty()) {
                if (email.isEmpty()) {
                    layoutTextInputEmail.error = "email Required"
                    layoutTextInputEmail.isErrorEnabled = true
                }
                if (pseudo.isEmpty()) {
                    layoutTextInputPseudo.error = "email Required"
                    layoutTextInputPseudo.isErrorEnabled = true
                }

                if (password.isEmpty()) {

                    layoutTextInputPassword.error = "Password required"
                    layoutTextInputPassword.isErrorEnabled = true
                }
                if (confirmPassword.isEmpty()) {

                    layoutTextInputConfirmPassword.error = "Password required"
                    layoutTextInputConfirmPassword.isErrorEnabled = true
                }
            } else {

                if (password != confirmPassword) {

                    layoutTextInputConfirmPassword.error = "Password didn't match!"
                    layoutTextInputConfirmPassword.isErrorEnabled = true
                } else {
                    auth.createUserWithEmailAndPassword(email, password) .addOnCompleteListener{task->

                        if (task.isSuccessful){

                            val user = hashMapOf(
                                "pseudo" to pseudo,
                                "email" to email
                             )

                            val currentUser = auth.currentUser

                            val db = Firebase.firestore
                            db.collection("users").document(currentUser!!.uid).set(user).addOnSuccessListener {
                                Intent(this, HomeActivity::class.java).also {
                                    startActivity(it)
                                }
                            }.addOnFailureListener {
                                layoutTextInputConfirmPassword.error="An error occured please try again"
                                layoutTextInputConfirmPassword.isErrorEnabled=true
                            }





                        }else{

                            layoutTextInputConfirmPassword.error="An error occured please try again"
                            layoutTextInputConfirmPassword.isErrorEnabled=true
                        }



                    }

                }
            }


        }

    }

    private fun initErrors() {
        layoutTextInputEmail.isErrorEnabled = false
        layoutTextInputPseudo.isErrorEnabled = false
        layoutTextInputPassword.isErrorEnabled = false
        layoutTextInputConfirmPassword.isErrorEnabled = false
    }


}