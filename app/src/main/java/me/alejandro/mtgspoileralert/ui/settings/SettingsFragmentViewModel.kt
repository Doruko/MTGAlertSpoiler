package me.alejandro.mtgspoileralert.ui.settings

import androidx.lifecycle.MutableLiveData
import me.alejandro.mtgspoileralert.data.worker.SyncWorkerFactory
import me.alejandro.mtgspoileralert.domain.base.BaseViewModel
import javax.inject.Inject

class SettingsFragmentViewModel : BaseViewModel() {

    @Inject
    lateinit var workerFactory: SyncWorkerFactory

    val worker: MutableLiveData<SyncWorkerFactory> = MutableLiveData()

    fun provideFactory() {
        worker.value = workerFactory
    }
}