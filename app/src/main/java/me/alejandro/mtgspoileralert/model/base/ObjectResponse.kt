package me.alejandro.mtgspoileralert.model.base

import com.squareup.moshi.Json

open class ObjectResponse{
    @Json(name = "id")
    val uuid: String? = null
}
