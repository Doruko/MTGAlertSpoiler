package me.alejandro.mtgspoileralert.data.network

import me.alejandro.mtgspoileralert.domain.model.card.CardResponse
import me.alejandro.mtgspoileralert.domain.model.set.SetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ScryfallApi {

    @GET("/sets")
    suspend fun getSets(): SetResponse

    @GET("/cards/search")
    suspend fun getCards(
        @Query("order") set: String,
        @Query("q") code: String,
        @Query("unique") unique: String,
        @Query("order") order: String
    ): CardResponse
}