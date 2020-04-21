package me.alejandro.mtgspoileralert.model.card

import me.alejandro.mtgspoileralert.model.base.BaseResponse

data class CardResponse (
    val data: List<Card>
): BaseResponse()