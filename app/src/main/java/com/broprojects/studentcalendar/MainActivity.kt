package com.broprojects.studentcalendar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.broprojects.studentcalendar.databinding.ActivityMainBinding
import com.broprojects.studentcalendar.main.MainFragmentDirections

interface ToolbarActivity {
    fun hideActionBar()
    fun hideActionBarAnimation()
    fun showActionBarAnimation()

    fun hideActionBarIcon()
    fun showActionBarIcon()

    fun setBackground(resourceId: Int)
    fun setActionBarText(stringId: Int)
    fun setActionBarIcon(iconId: Int)
}

class MainActivity : AppCompatActivity(), ToolbarActivity {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    private val animationDuration = 300L
    private var whiteTheme = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Setup navigation by navigation drawer
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_host_fragment)
        navController = navHostFragment!!.findNavController()
        drawerLayout = binding.drawerLayout
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        // Setup custom toolbar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, drawerLayout)

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val showWelcome = preferences.getBoolean(getString(R.string.show_welcome_preference), true)
        whiteTheme = preferences.getBoolean(getString(R.string.theme_preference), false)

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

    @Suppress("RestrictedApi")
    override fun setActionBarIcon(iconId: Int) {
        val icon = ResourcesCompat.getDrawable(resources, iconId, applicationContext.theme)
        findViewById<ActionMenuItemView>(R.id.welcome_button).setIcon(icon)
    }
}