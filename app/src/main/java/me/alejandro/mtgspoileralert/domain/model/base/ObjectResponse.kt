package me.alejandro.mtgspoileralert.domain.model.base

import com.squareup.moshi.Json

open class ObjectResponse{
    @Json(name = "id")
    val uuid: String? = null
}
