package me.alejandro.mtgspoileralert.domain.model.base

import com.squareup.moshi.Json

open class BaseResponse{
    @Json(name = "object")
    val object_type: String? = null
    val total_cards: Int? = null
    val has_more: Boolean? = null
    val next_page: String? = null
}