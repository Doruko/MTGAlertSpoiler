package me.alejandro.mtgspoileralert.ui.cardList

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.runBlocking
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.base.BaseViewModel
import me.alejandro.mtgspoileralert.model.card.CardResponse
import me.alejandro.mtgspoileralert.network.ScryfallApi
import java.lang.Exception
import javax.inject.Inject

class CardListViewModel(private val setCode: String) : BaseViewModel() {
    @Inject
    lateinit var scryfallApi: ScryfallApi

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val cardListAdapter: CardListAdapter = CardListAdapter()

    val errorClickListener = View.OnClickListener { loadCards(setCode) }

    init {
        loadCards(setCode)
    }

    private fun loadCards(code: String) {
        onRetrieveCardListStart()
        viewModelScope.launch {
            try{
                val res = scryfallApi.getCards(code = "e:$code")
                onRetrieveCardListSuccess(res)
            }catch (e: Exception){
                onRetrieveCardListError()
            } finally {
                onRetrieveCardListFinish()
            }
        }
    }

    private fun onRetrieveCardListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveCardListError() {
        errorMessage.value = R.string.card_error
    }

    private fun onRetrieveCardListSuccess(response: CardResponse) {
        cardListAdapter.updateSetList(response.data)
    }

    private fun onRetrieveCardListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }
}
