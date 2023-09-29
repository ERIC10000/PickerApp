package com.example.wastemanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.wastemanagerapp.helpers.ApiHelper
import com.example.wastemanagerapp.helpers.Constant
import com.example.wastemanagerapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val link : TextView = findViewById(R.id.Quality)
        link.setOnClickListener {
            val intent = Intent(applicationContext , RegisterActivity::class.java)
            startActivity(intent)
        }
        val email : EditText = findViewById(R.id.emailLogin)

        val password : EditText = findViewById(R.id.passwordLogin)

        val login: AppCompatButton = findViewById(R.id.btn_login)

        login.setOnClickListener {
            if (email.text.isEmpty() || password.text.isEmpty()){
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_LONG).show()

            }else{
                login(email , password)

            }
        }



    }

    private  fun  login( email : EditText , password : EditText){
        val helper = ApiHelper(this)
        val api = Constant.BASE_URL + "/picker_login"
        val body = JSONObject()
        body.put("email",email.text.toString())
        body.put("password",password.text.toString())
        helper.post(api , body , object:ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()

                val message = result!!.getJSONObject("message")

                val cardId = message!!.getString("DriverID")
                val firstName = message.getString("First Name")
                val county = message.getString("County")
                val constituency = message.getString("Constituency")
                val idNumb = message.getString("Idnumb")
                val phone = message.getString("MobileNumber")


                PrefsHelper.savePrefs(applicationContext , "cardId",cardId)
                PrefsHelper.savePrefs(applicationContext , "firstName", firstName)
                PrefsHelper.savePrefs(applicationContext , "county",county)
                PrefsHelper.savePrefs(applicationContext , "constituency",constituency)
                PrefsHelper.savePrefs(applicationContext , "idNumb",idNumb)
                PrefsHelper.savePrefs(applicationContext , "phone",phone)

                val intent = Intent(applicationContext , HomeActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(result: String?) {

            }
        })

    }
}