package me.alejandro.mtgspoileralert.ui.splash

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.alejandro.mtgspoileralert.domain.base.BaseViewModel

class SplashViewModel : BaseViewModel() {

    val nextStep: MutableLiveData<SplashState> = MutableLiveData()


    init {
        GlobalScope.launch {
            delay(3000)
            nextStep.postValue(SplashState.SetLists)
        }
    }
}

sealed class SplashState {
    object SetLists : SplashState()
}