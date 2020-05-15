package me.alejandro.mtgspoileralert.injection.component

import dagger.Component
import me.alejandro.mtgspoileralert.injection.module.NetworkModule
import me.alejandro.mtgspoileralert.ui.cardList.CardListAndroidViewModel
import me.alejandro.mtgspoileralert.ui.setList.SetListAndroidViewModel
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjector {

    fun inject(setListViewModel: SetListAndroidViewModel)
    fun inject(cardListViewModel: CardListAndroidViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}