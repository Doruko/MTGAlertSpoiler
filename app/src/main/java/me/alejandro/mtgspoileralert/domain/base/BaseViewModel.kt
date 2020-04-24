package me.alejandro.mtgspoileralert.domain.base

import androidx.lifecycle.ViewModel
import me.alejandro.mtgspoileralert.injection.component.DaggerViewModelInjector
import me.alejandro.mtgspoileralert.injection.component.ViewModelInjector
import me.alejandro.mtgspoileralert.injection.module.NetworkModule
import me.alejandro.mtgspoileralert.ui.cardList.CardListViewModel
import me.alejandro.mtgspoileralert.ui.setList.SetListViewModel

abstract class BaseViewModel: ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject(){
        when(this){
            is SetListViewModel -> injector.inject(this)
            is CardListViewModel -> injector.inject(this)
        }
    }
}