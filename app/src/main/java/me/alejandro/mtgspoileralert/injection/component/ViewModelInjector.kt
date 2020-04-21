package me.alejandro.mtgspoileralert.injection.component

import dagger.Component
import me.alejandro.mtgspoileralert.injection.module.NetworkModule
import me.alejandro.mtgspoileralert.ui.cardList.CardListViewModel
import me.alejandro.mtgspoileralert.ui.setList.SetListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject (setListViewModel: SetListViewModel)
    fun inject (cardListViewModel: CardListViewModel)

    @Component.Builder
    interface Builder{
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}