package com.example.wastemanagerapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wastemanagerapp.adapters.NotificationAdapter
import com.example.wastemanagerapp.models.Notification


class NotificationFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var notificationAdapter: NotificationAdapter
    lateinit var itemList : List<Notification>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        recyclerView = view.findViewById(R.id.recylerView)
        notificationAdapter = NotificationAdapter(notification())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = notificationAdapter
        return view
    }

    fun notification(): List<Notification>{
        itemList =  listOf(
            Notification( "12:00", "General Meeting", "Manager", "Please lets meet for a metting happening " +
                    "during lunch time, remember to bring your Member IDS"),
            Notification( "14:00", "Brief Meeting", "Manager", "Please lets meet for a metting happening " +
                    "during lunch time, remember to bring your Member IDS"),
            Notification( "00:16", "CEO Meeting", "Manager", "Please lets meet for a metting happening " +
                    "during lunch time, remember to bring your Member IDS"),
            Notification( "18:00", "Board Meeting", "Manager", "Please lets meet for a metting happening " +
                    "during lunch time, remember to bring your Member IDS"),
            Notification( "12:09", "Brief Meeting", "Manager", "Please lets meet for a metting happening " +
                    "during lunch time, remember to bring your Member IDS"),
        )

        return itemList
    }

}