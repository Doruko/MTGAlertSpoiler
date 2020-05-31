package me.alejandro.mtgspoileralert.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import androidx.work.*
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.data.worker.SyncDataWorker
import me.alejandro.mtgspoileralert.injection.viewModelFactory
import me.alejandro.mtgspoileralert.ui.MainActivity
import me.alejandro.mtgspoileralert.utils.CALL_PERIOD_PREFERENCE
import me.alejandro.mtgspoileralert.utils.CALL_PREFERENCE
import java.util.concurrent.TimeUnit


class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var viewModel: SettingsFragmentViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val activity = context as MainActivity
        activity.toolbar.setNavigationIcon(R.drawable.ic_back)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)

        viewModel = ViewModelProvider(
            this,
            viewModelFactory {
                SettingsFragmentViewModel()
            }
        ).get(
            SettingsFragmentViewModel::class.java
        )

        viewModel.worker.observe(viewLifecycleOwner, Observer { factory ->
            performSafeWorkManagerAction(actionIfNotInitialized = {
                WorkManager.initialize(
                    requireContext(),
                    Configuration.Builder().setMinimumLoggingLevel(Log.INFO)
                        .setWorkerFactory(factory).build()
                )
            })

            val c = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val work = PeriodicWorkRequestBuilder<SyncDataWorker>(
                preferenceManager.sharedPreferences.getInt(
                    CALL_PERIOD_PREFERENCE, 15
                ).toLong(), TimeUnit.MINUTES
            )
                .setConstraints(c)
                .build()

            val workManager = WorkManager.getInstance(requireContext())
            workManager.enqueue(work)

        })

        preferenceScreen.findPreference<SeekBarPreference>(CALL_PERIOD_PREFERENCE)?.isEnabled =
            preferenceManager.sharedPreferences.getBoolean(
                CALL_PREFERENCE, false
            )

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(prefereneces: SharedPreferences, key: String?) {
        when (key) {
            CALL_PREFERENCE -> {
                if (prefereneces.getBoolean(CALL_PREFERENCE, false)) {
                    preferenceScreen.findPreference<SeekBarPreference>(CALL_PERIOD_PREFERENCE)?.isEnabled =
                        true
                    viewModel.provideFactory()
                } else {
                    preferenceScreen.findPreference<SeekBarPreference>(CALL_PERIOD_PREFERENCE)?.isEnabled =
                        false
                    performSafeWorkManagerAction(actionIfInitialized = {
                        WorkManager.getInstance(requireContext()).cancelAllWork()
                    })
                }
            }
        }
    }

    private fun performSafeWorkManagerAction(
        actionIfInitialized: (() -> Unit)? = null,
        actionIfNotInitialized: (() -> Unit)? = null
    ) {
        try {
            WorkManager.getInstance(requireContext())
            actionIfInitialized?.invoke()
        } catch (e: Exception) {
            actionIfNotInitialized?.invoke()
        }
    }

}


