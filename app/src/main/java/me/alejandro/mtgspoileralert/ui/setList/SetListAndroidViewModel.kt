package me.alejandro.mtgspoileralert.ui.setList

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.data.usecases.GetSetsUseCase
import me.alejandro.mtgspoileralert.domain.base.BaseAndroidViewModel
import me.alejandro.mtgspoileralert.domain.base.Failure
import me.alejandro.mtgspoileralert.domain.model.set.Set
import me.alejandro.mtgspoileralert.domain.model.set.SetType
import me.alejandro.mtgspoileralert.utils.CARDS_PREFERENCE
import me.alejandro.mtgspoileralert.utils.LATEST_SET_PREFERENCE
import javax.inject.Inject

class SetListAndroidViewModel(application: Application) : BaseAndroidViewModel(application) {

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
        val context = getApplication<Application>().applicationContext
        val prefs = context.getSharedPreferences(CARDS_PREFERENCE, 0)

        list.filter {
            it.set_type == SetType.EXPANSION || it.set_type == SetType.CORE
        }.also {
            prefs.edit()
                .putString(LATEST_SET_PREFERENCE, it.first().code)
                .apply()
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