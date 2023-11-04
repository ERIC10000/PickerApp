package com.example.wastemanagerapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas

import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.wastemanagerapp.helpers.ApiHelper
import com.example.wastemanagerapp.helpers.Constant
import com.example.wastemanagerapp.helpers.PrefsHelper
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfName.Document
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document

import com.itextpdf.layout.element.Image
import org.json.JSONArray
import org.json.JSONObject

import java.io.ByteArrayOutputStream
import java.io.File


class HomeFragment : Fragment() {

    val IMAGE_URL = "https://www.pythonanywhere.com/user/pickerapp/files"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        getImage()
        val root =  inflater.inflate(R.layout.fragment_home, container, false)


        // open Drawer
        val cardOpenDrawer: CardView = root.findViewById(R.id.cardOpenDrawer)
        cardOpenDrawer.setOnClickListener {
            val drawer : ConstraintLayout = root.findViewById(R.id.drawerId)
            drawer.visibility = View.GONE
        }


        var link : TextView = root.findViewById(R.id.profile)
        link.setOnClickListener {
            Constant.navigate(ImageCapture() , requireContext()){
                startActivity(it)
            }
        }


        // close Drawer
        val cardCloseDrawer: LinearLayout = root.findViewById(R.id.closeDrawer)
        cardCloseDrawer.setOnClickListener {
//            Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()
            Constant.navigate( IntroductionActivity(),requireContext()){
                startActivity(it)
            }
        }

        // change Password Dialog
        val changePassDialog: LinearLayout = root.findViewById(R.id.changePassword)
        changePassDialog.setOnClickListener {

            val alertDialog = AlertDialog.Builder(requireActivity()).create()
            alertDialog.setTitle("")
            val view =
                LayoutInflater.from(requireActivity()).inflate(R.layout.change_password_dialog, null, false)
            alertDialog.setView(view)

            // radio button implementation here...


            alertDialog.show()
        }


        val image1 : ImageView = root.findViewById(R.id.smallImage)
        val image2 : ImageView = root.findViewById(R.id.bigImage)

        val imagePath = PrefsHelper.getPrefs(requireContext() , "stan")

        Glide.with(this)
            .load(IMAGE_URL + imagePath)
            .into(image1)

        Glide.with(this)
            .load(IMAGE_URL + imagePath)
            .into(image2)


        val cardId : TextView = root.findViewById(R.id.regnotagvalue)
        val memberID = PrefsHelper.getPrefs(requireContext(), "cardId")

        cardId.text = " $memberID"

        val cardName : TextView = root.findViewById(R.id.namevalue)
        val firstName = PrefsHelper.getPrefs(requireContext(), "firstName")

        val lastName = PrefsHelper.getPrefs(requireContext(), "lastName")
        cardName.text = " $firstName $lastName"


        val cardLocation : TextView = root.findViewById(R.id.countyvalue)
        val county = PrefsHelper.getPrefs(requireContext(), "county")

        cardLocation.text = "   $county"

        val cardConst : TextView = root.findViewById(R.id.wardvalue)
        val constituency = PrefsHelper.getPrefs(requireContext(), "constituency")

        cardConst.text = " $constituency"

        val nationalId : TextView = root.findViewById(R.id.idvalue)
        val idNumb = PrefsHelper.getPrefs(requireContext(), "idNumb").toInt()

        nationalId.text = " $idNumb"

        val telephoneNo : TextView = root.findViewById(R.id.telvalue)
        val phone = PrefsHelper.getPrefs(requireContext(), "phone")

        telephoneNo.text = " +254$phone"

        //id  card to pdf
        val download: AppCompatButton = root.findViewById(R.id.btn_login)
        download.setOnClickListener {
//                val progressBar: ProgressBar = root.findViewById(R.id.progressBar)
//                progressBar.visibility = View.VISIBLE
//                val pdfFileName = "IDCARD.pdf"
//                val pdfDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//
//                val pdfFile = File(pdfDirectory, pdfFileName)
//
//                val pdfDocument = PdfDocument(PdfWriter(pdfFile))
//                val pdf = Document(pdfDocument)
//
//                // Create a bitmap from your CardView
//                val cardView : CardView = root.findViewById(R.id.cardView)
//                val cardViewBitmap = viewToBitmap(cardView)
//
//                // Convert the bitmap to an iText Image
//                val image = Image(ImageDataFactory.create(cardViewBitmapToByteArray(cardViewBitmap)))
//                pdf.add(image)
//
//                // Close the document
//                pdf.close()
//                progressBar.visibility = View.GONE
//
//
//                // Now you can save the PDF file and provide a download link or any other functionality you want.
//            Toast.makeText(requireContext(), "Saved Successfully, Proceed to Downloads", Toast.LENGTH_LONG).show()

            val intent = Intent(requireContext(), PreviewIDActivity::class.java)
            startActivity(intent)


        }

        val profileImage = view?.findViewById<ImageView>(R.id.profileImage)
        val imagePath2 = PrefsHelper.getPrefs(requireContext() , "image")

        val name = view?.findViewById<TextView>(R.id.name)


        if (name != null) {
            name.text = firstName + "" + lastName
        }

        val county2 = view?.findViewById<TextView>(R.id.county)
        if (county2 != null) {
            county2.text = "County: $county"
        }

        if (profileImage != null) {
            Glide.with(this)
                .load(imagePath2)
                .into(profileImage)
        }

        return  root
    }

    private fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)
        return bitmap
    }

    private fun cardViewBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    private fun getImage(){
        val api = Constant.BASE_URL + "/get_image"
        val helper = ApiHelper(requireContext())
        val body = JSONObject()
        val id = PrefsHelper.getPrefs(requireContext() , "id").toInt()
        body.put("id" , id)
        helper.post(api , body , object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(requireContext(), result.toString(), Toast.LENGTH_SHORT).show()
                val image_path = result!!.getString("image_path")
                PrefsHelper.savePrefs(requireContext() , "stan" , image_path)
            }

            override fun onFailure(result: String?) {
                Toast.makeText(requireContext(), result.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}