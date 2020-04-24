package me.alejandro.mtgspoileralert.ui.setList

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.data.usecases.GetSetsUseCase
import me.alejandro.mtgspoileralert.domain.base.BaseViewModel
import me.alejandro.mtgspoileralert.domain.base.Failure
import me.alejandro.mtgspoileralert.domain.model.set.Set
import me.alejandro.mtgspoileralert.domain.model.set.SetType
import javax.inject.Inject

class SetListViewModel : BaseViewModel() {

    @Inject
    lateinit var getSetsUseCase: GetSetsUseCase

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val setListAdapter: SetListAdapter = SetListAdapter()

    val errorClickListener = View.OnClickListener { loadSets() }

    init {
        loadSets()
    }

    fun loadSets() {
        onRetrieveSetListStart()
        getSetsUseCase.invoke(viewModelScope, GetSetsUseCase.Params()) {
            it.fold(::handleFailure, ::handleSuccess)
            onRetrieveSetListFinish()
        }
    }

    private fun handleFailure(failure: Failure) {
        errorMessage.value = R.string.set_error
    }

    private fun handleSuccess(list: List<Set>) {
        list.filter {
            it.set_type == SetType.EXPANSION
        }.also {
            setListAdapter.updateSetList(it)
        }
    }

    private fun onRetrieveSetListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveSetListFinish() {
        loadingVisibility.value = View.GONE
    }

}