package com.example.wastemanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.wastemanagerapp.helpers.Constant
import com.example.wastemanagerapp.helpers.PrefsHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val next : AppCompatButton = findViewById(R.id.next)
        val firstName : TextInputLayout = findViewById(R.id.firstName)
        val inputName : TextInputEditText = findViewById(R.id.InputName)
        val lastName : TextInputLayout = findViewById(R.id.lastName)
        val inputothers : TextInputEditText = findViewById(R.id.InputOthers)
        val email : TextInputLayout = findViewById(R.id.email)
        val inputAddress : TextInputEditText = findViewById(R.id.InputAddress)
        val link : TextView = findViewById(R.id.Quality)
        link.setOnClickListener {
            val intent = Intent(applicationContext , LoginActivity::class.java)
            startActivity(intent)
        }


        inputAddress.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val emailInput : String = p0.toString()
                if (isEmailValid(emailInput)){
                    email.helperText = "Your email is valid"
                    email.error = ""

                    PrefsHelper.savePrefs(applicationContext,"firstName",inputName.text.toString())
                    PrefsHelper.savePrefs(applicationContext,"lastName",inputothers.text.toString())
                    PrefsHelper.savePrefs(applicationContext,"email",inputAddress.text.toString())

                    next.isEnabled = true



                }else if (emailInput.isEmpty()){
                    email.helperText = "Enter your email"
                    email.error = ""
                    next.isEnabled = false


                }

                else{
                    email.helperText = ""
                    email.error = "Your email is invalid"
                    next.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


        next.setOnClickListener {
            if ( inputName.text?.isEmpty() == true || inputothers.text?.isEmpty() == true || inputAddress.text?.isEmpty() == true){
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_LONG).show()


            }else{

                Constant.navigate(Register2(),applicationContext ){
                    startActivity(it)
                }


            }


        }
    }

    private  fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }
}