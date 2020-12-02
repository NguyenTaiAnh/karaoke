package com.example.karaoke_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("ImplicitThis")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.BtnNavBar)
        val navController = findNavController(R.id.fragment)
        //val appBarConfigguration = AppBarConfiguration(setOf(R.id.homeFragment , R.id.listFragment , R.id.mailFragment , R.id.profileFragment))
        //setupActionBarWithNavController(navController , appBarConfigguration)
        bottomNavigationView.setupWithNavController(navController)
    }
}