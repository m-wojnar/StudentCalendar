package com.broprojects.studentcalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        val navController = navHostFragment!!.findNavController()
        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainItem -> Toast.makeText(applicationContext, "Main", Toast.LENGTH_SHORT).show()
            R.id.settingsItem -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.aboutItem -> Toast.makeText(applicationContext, "About", Toast.LENGTH_SHORT).show()
            else -> return false
        }
        return true
    }
}