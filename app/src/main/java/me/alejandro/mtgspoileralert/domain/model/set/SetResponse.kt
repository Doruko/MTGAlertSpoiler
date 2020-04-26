package me.alejandro.mtgspoileralert.domain.model.set

import me.alejandro.mtgspoileralert.domain.model.base.BaseResponse

data class SetResponse(
    val data: List<Set>
): BaseResponse()