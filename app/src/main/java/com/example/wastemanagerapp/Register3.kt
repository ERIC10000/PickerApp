package com.example.wastemanagerapp

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.wastemanagerapp.helpers.ApiHelper
import com.example.wastemanagerapp.helpers.Constant
import com.example.wastemanagerapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class Register3 : AppCompatActivity() {
    lateinit var address : EditText
    lateinit var password : EditText
    lateinit var password2 : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register3)

        address  = findViewById(R.id.address)
        password  = findViewById(R.id.password)
        password2  = findViewById(R.id.password2)

        val firstName = PrefsHelper.getPrefs(this,"firstName")
        val lastName = PrefsHelper.getPrefs(this , "lastName")
        val email = PrefsHelper.getPrefs(this , "email")
        val constituency = PrefsHelper.getPrefs(this , "constituency")
        val county = PrefsHelper.getPrefs(this,"county")
        val idNumb = PrefsHelper.getPrefs(this , "idNumb")

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val next : TextView =  findViewById(R.id.next3)
        next.setOnClickListener {
            post_data(firstName , lastName , email , constituency , county , idNumb , progressBar )
        }
    }

    private fun post_data(firstName: String ,lastName: String ,email:String , constituency: String,
                          county:String , idNumb:String , progressBar: ProgressBar ){
        progressBar.visibility = View.VISIBLE
        val helper = ApiHelper(this)
        val api = Constant.BASE_URL + "/signup_new_picker"
        val body = JSONObject()
        body.put("firstName",firstName)
        body.put("lastName",lastName)
        body.put("county",county)
        body.put("constituency",constituency)
        body.put("mobileNumber",password.text)
        body.put("email",email)
        body.put("idNumb",idNumb)
        body.put("address",address.text.toString())
        body.put("password",password.text.toString())

        helper.post(api , body , object: ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                progressBar.visibility = View.GONE
                val intent = Intent(applicationContext , HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


}