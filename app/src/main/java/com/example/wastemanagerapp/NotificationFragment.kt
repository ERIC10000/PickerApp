package com.example.wastemanagerapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wastemanagerapp.adapters.NotificationAdapter
import com.example.wastemanagerapp.helpers.ApiHelper
import com.example.wastemanagerapp.helpers.Constant
import com.example.wastemanagerapp.models.Notification
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject


class NotificationFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var notificationAdapter: NotificationAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        recyclerView = view.findViewById(R.id.recylerView)
        notificationAdapter = NotificationAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        getNotifications()

        return view
    }



    private fun getNotifications() {

        val helper = ApiHelper(requireContext())
        val api = Constant.BASE_URL + "/check_notification"
        helper.get(api , object:ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                val gson = GsonBuilder().create()
                val itemList = gson.fromJson(
                    result.toString(),
                    Array<Notification>::class.java
                ).toList()
                notificationAdapter.setListItems(itemList)
                recyclerView.adapter = notificationAdapter
                Log.d("meso",itemList.toString())
            }

            override fun onSuccess(result: JSONObject?) {

            }

            override fun onFailure(result: String?) {

            }
        })


    }

}