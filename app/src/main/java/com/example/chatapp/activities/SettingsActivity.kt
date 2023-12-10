package com.example.chatapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.chatapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout

class SettingsActivity : AppCompatActivity() {



    lateinit var ivUser: ShapeableImageView
    private lateinit var layoutTextInputEmail : TextInputLayout
    private lateinit var layoutTextInputPseudo: TextInputLayout
    lateinit var btnSave:MaterialButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ivUser=findViewById(R.id.ivUser)
        layoutTextInputEmail =findViewById(R.id.tvuserEmail)
        layoutTextInputPseudo = findViewById(R.id.textInputLayoutpseudo)
        btnSave = findViewById(R.id.btnSave)

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){

            it?.let{
                ivUser.setImageURI(it)
            }

        }

        ivUser.setOnClickListener{

            pickImage.launch("image/*")

        }


    }



}

