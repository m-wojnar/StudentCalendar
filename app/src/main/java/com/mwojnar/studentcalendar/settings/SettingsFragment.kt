package com.mwojnar.studentcalendar.settings

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.mwojnar.studentcalendar.R

private const val IMPORT_CODE = 1
private const val EXPORT_CODE = 2

class PreferencesFragment : PreferenceFragmentCompat() {
    private lateinit var viewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val viewModelFactory = SettingsViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]

        val themePreference = preferenceScreen.findPreference<SwitchPreference>(getString(R.string.theme_preference))
        themePreference?.onPreferenceChangeListener = OnThemeChangeListener()

        val importPreference = preferenceScreen.findPreference<Preference>(getString(R.string.import_preference))
        importPreference?.onPreferenceClickListener = OnImportClickListener(requireContext(), this)

        val exportPreference = preferenceScreen.findPreference<Preference>(getString(R.string.export_preference))
        exportPreference?.onPreferenceClickListener = OnExportClickListener(requireContext(), this)

        viewModel.importSucceeded.observe(this, {
            booleanToast(it, R.string.import_completed, R.string.import_failed)
        })

        viewModel.exportSucceeded.observe(this, {
            booleanToast(it, R.string.export_completed, R.string.export_failed)
        })
    }

    private fun booleanToast(succeeded: Boolean?, trueStringId: Int, falseStringId: Int) {
        if (succeeded == true) {
            Toast.makeText(context, context?.getString(trueStringId), Toast.LENGTH_LONG).show()
        } else if (succeeded == false) {
            Toast.makeText(context, context?.getString(falseStringId), Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMPORT_CODE -> viewModel.importData(data?.data)
                EXPORT_CODE -> viewModel.exportData(data?.data)
            }
        }
    }

    private class OnThemeChangeListener : Preference.OnPreferenceChangeListener {
        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            val currentTheme = AppCompatDelegate.getDefaultNightMode()

            if (currentTheme == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            return true
        }
    }

    private class OnImportClickListener(private val context: Context, private val fragment: Fragment) :
        Preference.OnPreferenceClickListener {

        override fun onPreferenceClick(preference: Preference?): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Toast.makeText(context, context.getString(R.string.not_supported), Toast.LENGTH_LONG).show()
                return true
            }

            if (requestStoragePermissions(context, fragment.requireActivity())) {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "application/json"
                fragment.startActivityForResult(
                    Intent.createChooser(intent, context.getString(R.string.choose_file)), IMPORT_CODE
                )
            }

            return true
        }
    }

    private class OnExportClickListener(private val context: Context, private val fragment: Fragment) :
        Preference.OnPreferenceClickListener {

        override fun onPreferenceClick(preference: Preference?): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Toast.makeText(context, context.getString(R.string.not_supported), Toast.LENGTH_LONG).show()
                return true
            }

            if (requestStoragePermissions(context, fragment.requireActivity())) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                fragment.startActivityForResult(
                    Intent.createChooser(intent, context.getString(R.string.choose_directory)), EXPORT_CODE
                )
            }

            return true
        }
    }
}

internal fun requestStoragePermissions(context: Context, activity: Activity) =
    if (ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    ) {
        true
    } else {
        Toast.makeText(context, context.getString(R.string.grant_access), Toast.LENGTH_LONG).show()
        ActivityCompat.requestPermissions(activity, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), 0)
        false
    }
