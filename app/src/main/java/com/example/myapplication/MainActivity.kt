package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Video
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val IMAGE : Int=0
    val VIDEO : Int=1
    lateinit var uri: Uri
    lateinit var mstorage:StorageReference
    lateinit var mfiredb: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image_btn = findViewById<View>(R.id.btn1) as Button
        val video_btn = findViewById<View>(R.id.btn2) as Button

        var mstorage = FirebaseStorage.getInstance().reference

        image_btn.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Image"), IMAGE)
        })
        video_btn.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent()
            intent.setType("Videos/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Video"), VIDEO)
        })

        @Override

        fun onActivityResult(request_code: Int, result_code: Int, data: Intent?) {

            if (result_code == Activity.RESULT_OK) {
                if (request_code == IMAGE) {
                    uri = data!!.data
                    uri.toString()
                    upload()
                } else if (request_code == VIDEO) {
                    uri = data!!.data
                    uri.toString()
                    upload()
                }
            }
            super.onActivityResult(request_code, result_code, data)
        }
    }
        fun upload(){
            var mReference=mstorage.child("explore").child("35CbQX4ZMjPzJVWzmFMAD8g5eus2").child("image.jpg")
            try {
                mReference.putFile(uri).addOnSuccessListener {
                         mReference!!.downloadUrl.addOnSuccessListener {
                             it.toString()
                             var map=HashMap<String,String>()
                             map.put("URL",it.toString())
                             mfiredb= FirebaseDatabase.getInstance()
                             var ref=mfiredb.getReference().child("USERS").child("35CbQX4ZMjPzJVWzmFMAD8g5eus2").child("Images").setValue(map)

                }

                    Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
                }
            }catch(e:Exception){
                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()

            }

        }




    }

