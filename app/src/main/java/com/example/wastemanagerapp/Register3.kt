package com.example.wastemanagerapp

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import com.example.wastemanagerapp.helpers.ApiHelper
import com.example.wastemanagerapp.helpers.Constant
import com.example.wastemanagerapp.helpers.PrefsHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import org.json.JSONObject
import java.util.regex.Matcher
import java.util.regex.Pattern


class Register3 : AppCompatActivity() {
    lateinit var address : TextInputLayout
    lateinit var password : TextInputLayout
    lateinit var input_Password : TextInputEditText
    lateinit var password2 : TextInputLayout
    lateinit var input_Password2 : TextInputEditText
    lateinit var input_place : TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register3)

        address  = findViewById(R.id.address)
        input_Password = findViewById(R.id.InputPassword)

        password  = findViewById(R.id.password)

        password2  = findViewById(R.id.password2)

        input_place = findViewById(R.id.InputPlace)

        input_Password2 = findViewById(R.id.InputPassword2)

        input_Password.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val passwordInput : String = p0.toString()
                if (passwordInput.length >= 8){
                    val pattern : Pattern = Pattern.compile("[^a-zA-Z0-9]")
                    val matcher : Matcher = pattern.matcher(passwordInput)
                    val passwordsMatch : Boolean = matcher.find()
                    if (passwordsMatch){
                        password.helperText = "Your Password is Strong "
                        password.error = ""

                    }else{
                        password.error = "Use mix of upper and lower case , number and symbols "

                    }


                }
                else{
                    password.helperText = "Password must be 8 characters long"
                    password.error = ""
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        input_Password2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val passwordInput : String = p0.toString()
                if (passwordInput == input_Password.text.toString()){

                        password2.helperText = "Your Passwords match "
                        password2.error = ""

                }
                else if (passwordInput.isEmpty()){

                    password2.helperText = "Confirm your password "
                    password2.error = ""


                }
                else {
                    password2.error = "Your Passwords do not match"
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        val checkBox : AppCompatCheckBox = findViewById(R.id.agreement)
        val button : AppCompatButton = findViewById(R.id.complete)


        button.isEnabled = false

        checkBox.setOnCheckedChangeListener { _, isChecked ->

            val alertDialog = AlertDialog.Builder(this@Register3).create()
            alertDialog.setTitle("")
            val view =
                LayoutInflater.from(this@Register3).inflate(R.layout.terms_and_conditions, null, false)
            alertDialog.setView(view)

            // radio button implementation here...
            val acceptRadio = view.findViewById<RadioButton>(R.id.accept)
            val declineRadio = view.findViewById<RadioButton>(R.id.decline)

            alertDialog.show()

            acceptRadio.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Accept button selected, dismiss the dialog
                    alertDialog.dismiss()
                    button.isEnabled = true
                }
            }

            declineRadio.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Decline button selected, handle the action to go to the main activity
                    alertDialog.dismiss()
                    // Add code here to start the main activity
                    Constant.navigate(IntroductionActivity(),this ){
                        startActivity(intent)
                    }
                }
            }


            alertDialog.show()



        }

        val firstName = PrefsHelper.getPrefs(this,"firstName")
        val lastName = PrefsHelper.getPrefs(this , "lastName")
        val email = PrefsHelper.getPrefs(this , "email")
        val constituency = PrefsHelper.getPrefs(this , "constituency")
        val county = PrefsHelper.getPrefs(this,"county")
        val idNumb = PrefsHelper.getPrefs(this , "idNumber")
        val mobileNumber = PrefsHelper.getPrefs(this,"mobileNumber")

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val next : AppCompatButton =  findViewById(R.id.complete)
        next.setOnClickListener {
            if (input_place.text?.isEmpty() == true || input_Password.text?.isEmpty() == true) {
                Toast.makeText(
                    applicationContext,
                    "Please fill in all the fields",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                if (input_Password.text.toString() != input_Password2.text.toString()) {
                    Toast.makeText(
                        applicationContext,
                        "Your Password do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    post_data(
                        firstName,
                        lastName,
                        email,
                        constituency,
                        county,
                        idNumb,
                        progressBar,
                        mobileNumber
                    )
                    notifyAndUpdate(firstName, lastName, mobileNumber, "500", county)

                    if (input_Password.text.toString() != input_Password2.text.toString()) {
                        Toast.makeText(
                            applicationContext,
                            "Your Password do not match",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        post_data(
                            firstName,
                            lastName,
                            email,
                            constituency,
                            county,
                            idNumb,
                            progressBar,
                            mobileNumber
                        )

                    }



                }
            }
        }
    }

    private fun post_data(firstName: String ,lastName: String ,email:String , constituency: String,
                          county:String , idNumb:String , progressBar: ProgressBar , mobileNumber: String){
        progressBar.visibility = View.VISIBLE
        val helper = ApiHelper(this)
        val api = Constant.BASE_URL + "/signup_new_picker"
        val body = JSONObject()
        body.put("firstName",firstName)
        body.put("lastName",lastName)
        body.put("county",county)
        body.put("constituency",constituency)
        body.put("mobileNumber",mobileNumber)
        body.put("email",email)
        body.put("idNumb",idNumb)
        body.put("address",input_place.text.toString())
        body.put("password",input_Password.text.toString())

        helper.post(api , body , object: ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                progressBar.visibility = View.GONE
//                val intent = Intent(applicationContext , HomeActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//                finish()
                PrefsHelper.clearPrefs(applicationContext)

                val alertDialog = AlertDialog.Builder(this@Register3).create()
                alertDialog.setTitle("")
                val view =
                    LayoutInflater.from(this@Register3).inflate(R.layout.payment_alert_dialog, null, false)
                alertDialog.setView(view)
                val phone = view.findViewById<EditText>(R.id.emailLogin1)

                view.findViewById<Button>(R.id.pay).setOnClickListener {
                    mpesaPayment(phone.text.toString())

                    alertDialog.dismiss()
                }

                alertDialog.show()
            }

            override fun onFailure(result: String?) {

                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                Log.d("meso",result.toString())
            }
        })
    }

    private fun mpesaPayment (phone: String ){
        val api = Constant.BASE_URL + "/making_contributions"
        val helper = ApiHelper(this)
        val body = JSONObject()
        body.put("phone",phone)
        body.put("amount","100")
        helper.post(api , body , object:ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun notifyAndUpdate(firstName:String , lastName: String , phone: String , amount: String , county: String) {
        val helper = ApiHelper(this)
        val body1 = JSONObject()
        val api1 = Constant.BASE_URL + "add_approval_notification"
        body1.put("firstName", firstName)
        body1.put("lastName", lastName)
        helper.post(api1, body1, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        val body2 = JSONObject()
        val api2 = Constant.BASE_URL + "upload_registration_details"
        body2.put("firstName", firstName)
        body2.put("lastName", lastName)
        body2.put("phoneNumber", phone)
        body2.put("amount", amount)
        body2.put("county", county)

        helper.post(api2, body2, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

//        fun isPasswordValid(password: String): Boolean {
//            val passwordRegex = "^(?=.*[A-Z])(?=.*[!@#\\$%^&*]).{8,}\$"
//            val pattern = Pattern.compile(passwordRegex)
//            return pattern.matcher(password).matches()
//
//        }



}


