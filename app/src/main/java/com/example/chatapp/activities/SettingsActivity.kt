package com.example.chatapp.activities

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.chatapp.R
import com.example.chatapp.models.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.UUID

class SettingsActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var currentUser: FirebaseUser? = null

    lateinit var ivUser: ShapeableImageView
    private lateinit var layoutTextInputEmail: TextInputLayout
    private lateinit var layoutTextInputPseudo: TextInputLayout
    lateinit var btnSave: MaterialButton
    var isImageChanged = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        auth = Firebase.auth
        db = Firebase.firestore
        currentUser = auth.currentUser

        ivUser = findViewById(R.id.ivUser)
        layoutTextInputEmail = findViewById(R.id.tvuserEmail)
        layoutTextInputPseudo = findViewById(R.id.textInputLayoutpseudo)
        btnSave = findViewById(R.id.btnSave)

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {

            it?.let {
                Glide.with(this).load(it).placeholder(R.drawable.baseline_bug_report_24).into(ivUser)
                isImageChanged = true
            }

        }

        ivUser.setOnClickListener {

            pickImage.launch("image/*")

        }


        if (currentUser != null) {

            db.collection("users").document(currentUser!!.uid).get()
                .addOnSuccessListener { results ->
                    if (results != null) {
                        var user = results.toObject(com.example.chatapp.models.User::class.java)
                        user?.let {
                            user.uuid = currentUser!!.uid
                            setUserData(user)
                        }

                    }
                }
        } else {
            Log.d("SettingsActivity", "No user found")
        }


    }

    private fun setUserData(user: User) {
        layoutTextInputPseudo.editText?.setText(user.pseudo)
        layoutTextInputEmail.editText?.setText(user.email)
        // init image

        user.image?.let {
            Glide.with(this).load(it).placeholder(R.drawable.baseline_bug_report_24).into(ivUser)
        }


        btnSave.setOnClickListener {
            layoutTextInputPseudo.isErrorEnabled=false

            if (isImageChanged){
            uploadImageToFirebaseStorage(user)
            }else if( layoutTextInputPseudo.editText?.text.toString() != user.pseudo ){

                updateUserData(user)
            } else{

                Toast.makeText(this,"your informations are up to date !",Toast.LENGTH_LONG).show()
                layoutTextInputPseudo.clearFocus()
            }




        }

    }

    private fun uploadImageToFirebaseStorage(user: User) {
        var storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${user.uuid}")

        val bitmap = (ivUser.drawable as BitmapDrawable).bitmap

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,0,baos)
        val data = baos.toByteArray()

        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnSuccessListener {
                taskSnapShot ->
            imageRef.downloadUrl.addOnSuccessListener { uri->
                user.image = uri.toString()
                updateUserData(user)

            }
        }
    }

    private fun updateUserData(user: User) {
        var updateUser = hashMapOf<String, Any>(
            "pseudo" to layoutTextInputPseudo.editText?.text.toString(),
            "image" to (user.image?: "")

        )

        db.collection("users").document(user.uuid).update(updateUser).addOnSuccessListener {

            Toast.makeText(
                this, "Your infos are up to date !", Toast.LENGTH_LONG
            ).show()

        }.addOnFailureListener {
            layoutTextInputPseudo.error = "Error Occured"
            layoutTextInputPseudo.isErrorEnabled = true
        }
    }


}

