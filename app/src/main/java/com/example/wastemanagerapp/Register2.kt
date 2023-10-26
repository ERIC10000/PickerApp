package com.example.wastemanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.wastemanagerapp.helpers.Constant
import com.example.wastemanagerapp.helpers.PrefsHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class Register2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)


        val constituency : TextInputLayout = findViewById(R.id.Constituency)
        val mobileNumber : TextInputLayout = findViewById(R.id.mobileNumber)
        val idNumber : TextInputLayout = findViewById(R.id.idNumber)

        val inputward : TextInputEditText = findViewById(R.id.InputWard)
        val inputnumber : TextInputEditText = findViewById(R.id.InputNumber)
        val inputIdNumber : TextInputEditText = findViewById(R.id.InputIdNumber)
        val next : TextView = findViewById(R.id.next2)


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

                }

            }


        }


        //val county : EditText = findViewById(R.id.County)



        inputnumber.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val numberInput : String = p0.toString()
                if (isPhoneNumberValid(numberInput) ){
                    mobileNumber.helperText = "The Phone Number is valid"
                    mobileNumber.error = ""
                    PrefsHelper.savePrefs(applicationContext,"constituency",inputward.text.toString())
                    PrefsHelper.savePrefs(applicationContext,"mobileNumber",inputnumber.text.toString())
                    PrefsHelper.savePrefs(applicationContext,"idNumber", inputIdNumber.text.toString())
                    next.isEnabled = true
                }
                else if (numberInput.isEmpty()){
                    mobileNumber.helperText = "Enter the phone number "
                    mobileNumber.error = ""

                }
                else {
                    mobileNumber.helperText = ""
                    mobileNumber.error = "The phone number format entered is invalid"
                    next.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


        next.setOnClickListener {
            if (  inputward.text?.isEmpty() == true || inputnumber.text?.isEmpty() == true || inputIdNumber.text?.isEmpty() == true){
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_LONG).show()
            }else{

                Constant.navigate(Register3() , applicationContext ){
                    startActivity(it)
                }


            }

        }

    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        val phoneRegex = "^[71]\\d{8}\$"
        val pattern = Pattern.compile(phoneRegex)
        return pattern.matcher(phoneNumber).matches()
    }
}