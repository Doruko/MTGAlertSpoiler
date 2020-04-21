package me.alejandro.mtgspoileralert.network

import me.alejandro.mtgspoileralert.model.card.CardResponse
import me.alejandro.mtgspoileralert.model.set.Set
import me.alejandro.mtgspoileralert.model.set.SetResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScryfallApi {

    @GET("/sets")
    suspend fun getSets(): SetResponse

    @GET("/cards/search")
    suspend fun getCards(
        @Query("order") set: String = "set",
        @Query("q") code: String,
        @Query("unique") unique: String = "prints",
        @Query("order") order: String = "spoiled"
    ): CardResponse
}