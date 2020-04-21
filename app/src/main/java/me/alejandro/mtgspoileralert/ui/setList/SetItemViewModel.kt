package me.alejandro.mtgspoileralert.ui.setList

import androidx.lifecycle.MutableLiveData
import me.alejandro.mtgspoileralert.base.BaseViewModel
import me.alejandro.mtgspoileralert.model.set.Set

class SetItemViewModel: BaseViewModel() {
    private val setTitle = MutableLiveData<String>()
    private val setCode = MutableLiveData<String>()

    fun bind(set: Set){
        setTitle.value = set.name
        setCode.value = set.code
    }

    fun getSetTitle(): MutableLiveData<String> = setTitle
    fun getSetCode(): MutableLiveData<String> = setCode
}