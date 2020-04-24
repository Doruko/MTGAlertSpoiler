package me.alejandro.mtgspoileralert.domain.model.card

import me.alejandro.mtgspoileralert.domain.model.base.BaseResponse

data class CardResponse (
    val data: List<Card>
): BaseResponse()