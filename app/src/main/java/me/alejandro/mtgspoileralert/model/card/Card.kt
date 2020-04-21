package me.alejandro.mtgspoileralert.model.card

import com.squareup.moshi.Json
import me.alejandro.mtgspoileralert.model.base.ObjectResponse

data class Card (
    val lang: String,
    @Json(name = "object")
    val objectType: String,
    val oracle_id: String,
    val prints_search_uri: String,
    val rulings_uri: String,
    val scryfall_uri: String,
    val uri: String,
    val name: String,
    val image_uris: CardImageUri
): ObjectResponse()