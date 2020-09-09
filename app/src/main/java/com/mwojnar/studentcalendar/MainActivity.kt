package com.mwojnar.studentcalendar

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.mwojnar.studentcalendar.databinding.ActivityMainBinding
import com.mwojnar.studentcalendar.intro.StudentCalendarIntro
import com.mwojnar.studentcalendar.main.MainFragmentDirections

interface ToolbarActivity {
    fun hideActionBar()
    fun hideActionBarAnimation()
    fun showActionBarAnimation()

    fun hideActionBarIcon()
    fun showActionBarIcon()

    fun setBackground(resourceId: Int)
    fun setActionBarText(stringId: Int)
}

class MainActivity : AppCompatActivity(), ToolbarActivity {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private val animationDuration = 300L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val showIntro = preferences.getBoolean(getString(R.string.show_intro), true)

        // Go to intro screen only on first startup
        if (showIntro) {
            goToIntroActivity()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Setup navigation by navigation drawer
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_host_fragment)
        navController = navHostFragment!!.findNavController()
        drawerLayout = binding.drawerLayout
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        // Setup custom toolbar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, drawerLayout)

        val showWelcome = preferences.getBoolean(getString(R.string.show_welcome_preference), true)

        // Go to welcome screen on startup
        if (showWelcome) {
            goToWelcomeFragment()
        }
    }

    override fun onSupportNavigateUp() =
        NavigationUI.navigateUp(navController, drawerLayout)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.welcome_button -> goToWelcomeFragment()
        else -> super.onOptionsItemSelected(item)
    }

    private fun goToWelcomeFragment(): Boolean {
        navController.navigate(MainFragmentDirections.actionMainFragmentToWelcomeFragment())
        return true
    }

    private fun goToIntroActivity(): Boolean {
        preferences.edit().putBoolean(getString(R.string.show_intro), false).apply()
        startActivity(Intent(this, StudentCalendarIntro::class.java))
        return true
    }

    override fun hideActionBar() {
        binding.toolbar.animate()
            .setDuration(0L)
            .alpha(0.0f)
    }

    override fun hideActionBarAnimation() {
        binding.toolbar.animate()
            .setDuration(animationDuration)
            .alpha(0.0f)
    }

    override fun showActionBarAnimation() {
        binding.toolbar.animate()
            .setDuration(animationDuration)
            .alpha(1.0f)
    }

    override fun hideActionBarIcon() {
        findViewById<ActionMenuItemView>(R.id.welcome_button).visibility = View.INVISIBLE
    }

    override fun showActionBarIcon() {
        findViewById<ActionMenuItemView>(R.id.welcome_button).visibility = View.VISIBLE
    }

    override fun setBackground(resourceId: Int) {
        binding.linearLayout.setBackgroundResource(resourceId)
    }

    override fun setActionBarText(stringId: Int) {
        supportActionBar?.title = getString(stringId)
    }
}