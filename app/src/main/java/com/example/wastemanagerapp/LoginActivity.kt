package com.example.wastemanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.wastemanagerapp.helpers.ApiHelper
import com.example.wastemanagerapp.helpers.Constant
import com.example.wastemanagerapp.helpers.PrefsHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import org.json.JSONObject
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val link : TextView = findViewById(R.id.Quality)
        link.setOnClickListener {
//            val intent = Intent(applicationContext , RegisterActivity::class.java)
//            startActivity(intent)
            Constant.navigate(RegisterActivity() , this ){
                startActivity(it)
            }
        }
        val email : TextInputLayout = findViewById(R.id.emailLogin)
        val emailLogin : TextInputEditText = findViewById(R.id.InputLogin)

        val password : TextInputLayout = findViewById(R.id.passwordLogin)
        val passwordLogin : TextInputEditText = findViewById(R.id.InputPasswordLogin)

        val login: AppCompatButton = findViewById(R.id.btn_login)

        val progress: ProgressBar = findViewById(R.id.progress)



        login.setOnClickListener {
            if (emailLogin.text?.isEmpty() == true || passwordLogin.text?.isEmpty() == true) {
                Toast.makeText(
                    applicationContext,
                    "Please fill in all the fields",
                    Toast.LENGTH_LONG
                ).show()
            }

            else{

                    if (isEmailValid(emailLogin.text.toString()) || isPhoneNumberValid(emailLogin.text.toString())){

                            login(emailLogin , passwordLogin , progress)

                    }
                    else{
                        Toast.makeText(applicationContext, "The input entered is in an invalid format", Toast.LENGTH_LONG).show()
                    }

            }




        } // End setOnClickListener
    }// End onCreate

    private  fun  login( email : TextInputEditText , password : TextInputEditText , progressBar: ProgressBar){
        progressBar.visibility = View.VISIBLE
        val helper = ApiHelper(this)
        val api = Constant.BASE_URL + "/picker_login"
        val body = JSONObject()
        val data = format(email)
        body.put("email",data)
        body.put("password",password.text.toString())

        helper.post(api , body , object:ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Welcome", Toast.LENGTH_SHORT).show()

                val message = result!!.getJSONObject("message")

                val cardId = message!!.getString("DriverID")
                val firstName = message.getString("FirstName")
                val lastName = message.getString("LastName")
                val county = message.getString("County")
                val constituency = message.getString("Constituency")
                val idNumb = message.getInt("Idnumb")
                val phone = message.getInt("MobileNumber")


                PrefsHelper.savePrefs(applicationContext , "cardId",cardId)
                PrefsHelper.savePrefs(applicationContext , "firstName", firstName)
                PrefsHelper.savePrefs(applicationContext , "lastName", lastName)
                PrefsHelper.savePrefs(applicationContext , "county",county)
                PrefsHelper.savePrefs(applicationContext , "constituency",constituency)
                PrefsHelper.savePrefs(applicationContext , "idNumb",idNumb.toString())
                PrefsHelper.savePrefs(applicationContext , "phone",phone.toString())


                Constant.navigate(HomeActivity() , applicationContext ){
                    startActivity(it)
                }
            }

            override fun onFailure(result: String?) {
                progressBar.visibility = View.GONE
                Log.d("Hapa",result.toString())
                Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun format ( email: EditText) : Any{
        val userInput = email.text.toString()

// Check if the input is not empty and does not contain any digits
        if (userInput.isNotEmpty() && !userInput.any { it.isDigit() }) {
            // Input is a valid string
            // Do something with the string
            return userInput
        } else {
            // Input is not a valid string
            // Handle the case where the input is not a valid string
            return userInput.toInt()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        val phoneRegex = "^07\\d{8}\$"
        val pattern = Pattern.compile(phoneRegex)
        return pattern.matcher(phoneNumber).matches()
    }



//    fun isPasswordValid(password: String): Boolean {
//        val passwordRegex = "^(?=.*[A-Z])(?=.*[!@#\\$%^&*]).{8,}\$"
//        val pattern = Pattern.compile(passwordRegex)
//        return pattern.matcher(password).matches()
//    }



} // End class







