package com.example.wastemanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.wastemanagerapp.helpers.PrefsHelper

class PreviewIDActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_idactivity)

        val id : TextView = findViewById(R.id.regnotagvalue)
        val name : TextView = findViewById(R.id.namevalue)
        var idNo : TextView = findViewById(R.id.idvalue)
        val phone : TextView = findViewById(R.id.telvalue)
        val county : TextView = findViewById(R.id.countyvalue)
        val ward : TextView = findViewById(R.id.wardvalue)

        val image : ImageView = findViewById(R.id.bigImage)

        val cardId = PrefsHelper.getPrefs(this , "cardId")
        id.text = cardId

        val firstName = PrefsHelper.getPrefs(this , "firstName")
        val lastName = PrefsHelper.getPrefs(this , "lastName")

        name.text = firstName + "" + lastName

        val county2 = PrefsHelper.getPrefs(this , "county")
        county.text = county2


        val constituency = PrefsHelper.getPrefs(this , "constituency")
        ward.text = constituency

        val idNumb = PrefsHelper.getPrefs(this , "idNumb")
        idNo.text = idNumb

        val phone2 = PrefsHelper.getPrefs(this , "phone")
        phone.text = phone2

        val imagePath = PrefsHelper.getPrefs(applicationContext , "image")

        Glide.with(this)
            .load(imagePath)
            .into(image)






    }
}