package me.alejandro.mtgspoileralert.domain.model.card

data class CardImageUri(
    val small: String,
    val normal: String,
    val large: String,
    val png: String,
    val art_crop: String,
    val border_crop: String
)
