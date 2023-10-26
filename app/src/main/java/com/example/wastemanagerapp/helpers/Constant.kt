package com.example.wastemanagerapp.helpers

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.wastemanagerapp.HomeActivity
import com.itextpdf.kernel.pdf.navigation.PdfDestination

class Constant {
    companion object{
        val BASE_URL = "https://pickerapp.pythonanywhere.com/api"

        fun navigate(destination: AppCompatActivity, context : Context, intentFunction : (Intent) -> Unit){
            val intent = Intent(context , destination::class.java)
            intentFunction(intent)

        }
    }
}