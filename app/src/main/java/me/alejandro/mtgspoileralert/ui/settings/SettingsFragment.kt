package me.alejandro.mtgspoileralert.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import me.alejandro.mtgspoileralert.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }


}