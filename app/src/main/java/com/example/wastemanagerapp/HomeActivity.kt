package com.example.wastemanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){

                R.id.home -> {
                    replaceFragment(HomeFragment())
                }

                R.id.search -> {
                    replaceFragment(SearchFragment())
                }


                R.id.cart -> {
                    replaceFragment(NotificationFragment())
                }

                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                }

            } // end when
            true
        }
        replaceFragment(HomeFragment())

//        bottomNavigation.getOrCreateBadge(R.id.bottom_reels).apply {
//            number = 5
//            isVisible = true
//        }




    }// end onCreate

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()
    }
}