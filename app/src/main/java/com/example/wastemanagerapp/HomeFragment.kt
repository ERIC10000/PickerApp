package com.example.wastemanagerapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.wastemanagerapp.helpers.PrefsHelper


class HomeFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        val root =  inflater.inflate(R.layout.fragment_home, container, false)






        val image1 : ImageView = root.findViewById(R.id.smallImage)
        val image2 : ImageView = root.findViewById(R.id.bigImage)

        val imagePath = PrefsHelper.getPrefs(requireContext() , "image")

        Glide.with(this)
            .load(imagePath)
            .into(image1)

        Glide.with(this)
            .load(imagePath)
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

        cardLocation.text = " $county"

        val cardConst : TextView = root.findViewById(R.id.wardvalue)
        val constituency = PrefsHelper.getPrefs(requireContext(), "constituency")

        cardConst.text = " $constituency"

        val nationalId : TextView = root.findViewById(R.id.idvalue)
        val idNumb = PrefsHelper.getPrefs(requireContext(), "idNumb")

        nationalId.text = " $idNumb"

        val telephoneNo : TextView = root.findViewById(R.id.telvalue)
        val phone = PrefsHelper.getPrefs(requireContext(), "phone")

        telephoneNo.text = " +254$phone"
        return  root
    }




}