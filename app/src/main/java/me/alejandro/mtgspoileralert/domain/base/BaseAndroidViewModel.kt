package me.alejandro.mtgspoileralert.domain.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import me.alejandro.mtgspoileralert.injection.component.DaggerViewModelInjector
import me.alejandro.mtgspoileralert.injection.component.ViewModelInjector
import me.alejandro.mtgspoileralert.injection.module.NetworkModule
import me.alejandro.mtgspoileralert.ui.cardList.CardListAndroidViewModel
import me.alejandro.mtgspoileralert.ui.setList.SetListAndroidViewModel

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is SetListAndroidViewModel -> injector.inject(this)
            is CardListAndroidViewModel -> injector.inject(this)
        }
    }
}