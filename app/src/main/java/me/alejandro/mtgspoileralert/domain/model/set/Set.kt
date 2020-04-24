package me.alejandro.mtgspoileralert.domain.model.set

import me.alejandro.mtgspoileralert.domain.model.base.ObjectResponse

data class Set(
    val code: String,
    val mtgo_code: String?,
    val tcgplayer_id: String?,
    val name: String,
    val set_type: SetType,
    val released_at: String?,
    val block_code: String?,
    val block: String?,
    val parent_set_code: String?,
    val card_count: Int,
    val digital: Boolean,
    val foil_only: Boolean,
    val scryfall_uri: String,
    val uri: String,
    val icon_svg_uri: String,
    val search_uri: String
): ObjectResponse()