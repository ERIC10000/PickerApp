package com.example.wastemanagerapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wastemanagerapp.helpers.PrefsHelper


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val root =  inflater.inflate(R.layout.fragment_home, container, false)
        val cardId : TextView = root.findViewById(R.id.cardID)
        val memberID = PrefsHelper.getPrefs(requireContext(), "cardId")

        cardId.text = "Member Id : $memberID"

        val cardName : TextView = root.findViewById(R.id.cardName)
        val firstName = PrefsHelper.getPrefs(requireContext(), "firstName")

        cardName.text = "First Name : $firstName"

        val last : TextView = root.findViewById(R.id.last)
        val lastName = PrefsHelper.getPrefs(requireContext(), "lastName")

        last.text = "Last Name : $lastName"

        val cardLocation : TextView = root.findViewById(R.id.cardLocation)
        val county = PrefsHelper.getPrefs(requireContext(), "county")

        cardLocation.text = "County : $county"

        val cardConst : TextView = root.findViewById(R.id.cardConst)
        val constituency = PrefsHelper.getPrefs(requireContext(), "constituency")

        cardConst.text = "Constituency : $constituency"

        val nationalId : TextView = root.findViewById(R.id.nationalID)
        val idNumb = PrefsHelper.getPrefs(requireContext(), "idNumb")

        nationalId.text = "National ID : $idNumb"

        val telephoneNo : TextView = root.findViewById(R.id.telephoneNo)
        val phone = PrefsHelper.getPrefs(requireContext(), "phone")

        telephoneNo.text = "Telephone: +254$phone"
        return  root
    }


}