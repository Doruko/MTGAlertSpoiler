package me.alejandro.mtgspoileralert.ui.cardList

import androidx.lifecycle.MutableLiveData
import me.alejandro.mtgspoileralert.domain.base.BaseViewModel
import me.alejandro.mtgspoileralert.domain.model.card.Card

class CardItemViewModel() : BaseViewModel() {
    private val cardUrl = MutableLiveData<String>()

    fun bind(card: Card){
        cardUrl.value = card.image_uris.normal
    }

    fun getCardUrl(): MutableLiveData<String> = cardUrl

}