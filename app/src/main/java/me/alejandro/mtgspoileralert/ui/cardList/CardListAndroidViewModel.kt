package me.alejandro.mtgspoileralert.ui.cardList

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.data.usecases.GetCardsUseCase
import me.alejandro.mtgspoileralert.domain.base.BaseAndroidViewModel
import me.alejandro.mtgspoileralert.domain.base.Failure
import me.alejandro.mtgspoileralert.domain.model.card.Card
import me.alejandro.mtgspoileralert.utils.CARDS_PREFERENCE
import me.alejandro.mtgspoileralert.utils.LATEST_RESPONSE_PREFERENCE
import javax.inject.Inject

class CardListAndroidViewModel(application: Application, private val setCode: String) :
    BaseAndroidViewModel(application), CardClickListener {

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
        val context = getApplication<Application>().applicationContext
        val prefs = context.getSharedPreferences(CARDS_PREFERENCE, 0)

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Card::class.java)
        val adapter = moshi.adapter<List<Card>>(type)

        prefs.edit()
            .putString(LATEST_RESPONSE_PREFERENCE, adapter.toJson(list))
            .apply()

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
