package me.alejandro.mtgspoileralert.ui.cardList.cardDialog

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Callback
import me.alejandro.mtgspoileralert.domain.base.BaseViewModel

class CardDialogViewModel() : BaseViewModel() {
    val cardUrl = MutableLiveData<String>()
    val cardCallback = MutableLiveData<Callback>()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    fun loadImage(url: String) {
        loadingVisibility.value = View.VISIBLE
        cardCallback.value = object : Callback {
            override fun onSuccess() {
                loadingVisibility.value = View.GONE
            }

            override fun onError(e: Exception?) {
                loadingVisibility.value = View.GONE
            }

        }
        cardUrl.value = url
    }
}