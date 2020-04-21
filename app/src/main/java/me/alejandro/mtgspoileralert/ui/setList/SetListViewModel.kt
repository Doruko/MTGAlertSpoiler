package me.alejandro.mtgspoileralert.ui.setList

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.base.BaseViewModel
import me.alejandro.mtgspoileralert.model.set.SetResponse
import me.alejandro.mtgspoileralert.model.set.SetType
import me.alejandro.mtgspoileralert.network.ScryfallApi
import java.lang.Exception
import javax.inject.Inject

class SetListViewModel() : BaseViewModel() {

    @Inject
    lateinit var scryfallApi: ScryfallApi

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val setListAdapter: SetListAdapter = SetListAdapter()

    val errorClickListener = View.OnClickListener { loadSets() }

    init {
        loadSets()
    }

    private fun loadSets() {
        onRetrieveSetListStart()
        viewModelScope.launch {
            try{
                val res = scryfallApi.getSets()
                onRetrieveSetListSuccess(res)
            }catch (e: Exception){
                onRetrieveSetListError()
            } finally {
                onRetrieveSetListFinish()
            }
        }
    }

    private fun onRetrieveSetListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveSetListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveSetListSuccess(response: SetResponse) {
        response.data.filter {
            it.set_type == SetType.EXPANSION
        }.also {
            setListAdapter.updateSetList(it)
        }
    }

    private fun onRetrieveSetListError() {
        errorMessage.value = R.string.set_error
    }
}