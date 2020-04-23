package me.alejandro.mtgspoileralert.ui.cardList

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.base.BaseViewModel
import me.alejandro.mtgspoileralert.model.card.CardResponse
import me.alejandro.mtgspoileralert.network.ScryfallApi
import javax.inject.Inject

class CardListViewModel(private val setCode: String) : BaseViewModel(), CardClickListener {
    @Inject
    lateinit var scryfallApi: ScryfallApi

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
        viewModelScope.launch {
            try{
                val res = scryfallApi.getCards(code = "e:$code")
                onRetrieveCardListSuccess(res)
            }catch (e: Exception){
                onRetrieveCardListError()
            } finally {
                onRetrieveCardListFinish(swipeUpdate)
            }
        }
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

    private fun onRetrieveCardListError() {
        errorMessage.value = R.string.card_error
    }

    private fun onRetrieveCardListSuccess(response: CardResponse) {
        cardListAdapter.updateSetList(response.data)
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
