package me.alejandro.mtgspoileralert.ui.cardList

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.data.usecases.GetCardsUseCase
import me.alejandro.mtgspoileralert.domain.base.BaseViewModel
import me.alejandro.mtgspoileralert.domain.base.Failure
import me.alejandro.mtgspoileralert.domain.model.card.Card
import javax.inject.Inject

class CardListViewModel(private val setCode: String) : BaseViewModel(), CardClickListener {

    @Inject
    lateinit var getCardsUseCase: GetCardsUseCase

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val cardBigUrl: MutableLiveData<String> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val cardListAdapter: CardListAdapter = CardListAdapter(this)

    val errorClickListener = View.OnClickListener { loadCards(setCode) }


    init {
        loadCards(setCode)
    }

    private fun loadCards(code: String, swipeUpdate: Boolean = false) {
        onRetrieveCardListStart(swipeUpdate)
        val params = GetCardsUseCase.Params(code = code)
        getCardsUseCase.invoke(viewModelScope, params) {
            it.fold(::handleFailure, ::handleSuccess)
            onRetrieveCardListFinish(swipeUpdate)
        }
    }

    private fun handleFailure(failure: Failure) {
        errorMessage.value = R.string.card_error
    }

    private fun handleSuccess(list: List<Card>) {
        cardListAdapter.updateSetList(list)
    }

    fun refreshCards() {
        loadCards(setCode, true)
    }

    private fun onRetrieveCardListFinish(swipeUpdate: Boolean) {
        if (swipeUpdate)
            isLoading.value = false
        else
            loadingVisibility.value = View.GONE
    }

    private fun onRetrieveCardListStart(swipeUpdate: Boolean) {
        if (swipeUpdate)
            isLoading.value = true
        else
            loadingVisibility.value = View.VISIBLE

        errorMessage.value = null
    }

    override fun cardClicked(largeUrl: String) {
        cardBigUrl.value = largeUrl
    }
}
