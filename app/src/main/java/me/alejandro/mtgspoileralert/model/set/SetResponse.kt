package me.alejandro.mtgspoileralert.model.set

import me.alejandro.mtgspoileralert.model.base.BaseResponse

data class SetResponse(
    val data: List<Set>
): BaseResponse()