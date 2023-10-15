package com.example.wastemanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.wastemanagerapp.helpers.PrefsHelper

class Register2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val languages = resources.getStringArray(R.array.Languages)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    PrefsHelper.savePrefs(applicationContext,"county",languages[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }


        }


        //val county : EditText = findViewById(R.id.County)
        val constituency : EditText = findViewById(R.id.Constituency)
        val mobileNumber : EditText = findViewById(R.id.mobileNumber)
        val idNumber : EditText = findViewById(R.id.idNumber)

        val next : TextView = findViewById(R.id.next2)
        next.setOnClickListener {
            if (  constituency.text.isEmpty() || mobileNumber.text.isEmpty() || idNumber.text.isEmpty()){
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_LONG).show()
            }else{

                PrefsHelper.savePrefs(this,"constituency",constituency.text.toString())
                PrefsHelper.savePrefs(this,"mobileNumber",mobileNumber.text.toString())
                PrefsHelper.savePrefs(this,"idNumber", idNumber.text.toString())
                val intent = Intent(applicationContext , Register3::class.java)
                startActivity(intent)
            }

        }

    }
}