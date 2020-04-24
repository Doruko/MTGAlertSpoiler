package me.alejandro.mtgspoileralert.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.alejandro.mtgspoileralert.data.network.ScryfallApi
import me.alejandro.mtgspoileralert.domain.model.card.CardResponse
import javax.inject.Inject

class CardsRepository @Inject constructor(private val api: ScryfallApi) {

    suspend fun getCards(set: String, code: String, unique: String, order: String): CardResponse =
        withContext(Dispatchers.IO) {
            api.getCards(set, "e:$code", unique, order)
        }

}