package me.alejandro.mtgspoileralert.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.alejandro.mtgspoileralert.data.network.ScryfallApi
import me.alejandro.mtgspoileralert.domain.model.set.SetResponse
import javax.inject.Inject

class SetsRepository @Inject constructor(private val api: ScryfallApi) {

    suspend fun getSets(): SetResponse = withContext(Dispatchers.IO) {
        api.getSets()
    }

}