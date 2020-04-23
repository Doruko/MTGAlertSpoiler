package me.alejandro.mtgspoileralert.ui.cardList.cardDialog

import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Callback
import me.alejandro.mtgspoileralert.base.BaseViewModel

class CardDialogViewModel : BaseViewModel() {
    val cardUrl = MutableLiveData<String>()
    val cardCallback = MutableLiveData<Callback>()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
}