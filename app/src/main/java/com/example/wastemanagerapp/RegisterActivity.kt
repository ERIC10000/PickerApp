package com.example.wastemanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.wastemanagerapp.helpers.PrefsHelper

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val next : TextView = findViewById(R.id.next)
        val firstName : EditText = findViewById(R.id.firstName)
        val lastName : EditText = findViewById(R.id.lastName)
        val email : EditText = findViewById(R.id.email)


        next.setOnClickListener {
            PrefsHelper.savePrefs(this,"firstName",firstName.text.toString())
            PrefsHelper.savePrefs(this,"lastName",lastName.text.toString())
            PrefsHelper.savePrefs(this,"email",email.text.toString())
            val intent = Intent(applicationContext , Register2::class.java)
            startActivity(intent)

        }
    }
}