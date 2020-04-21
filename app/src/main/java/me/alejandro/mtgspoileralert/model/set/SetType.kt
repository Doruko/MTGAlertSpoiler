package me.alejandro.mtgspoileralert.model.set

import com.squareup.moshi.Json

enum class SetType() {
    @Json(name="core")
    CORE,
    @Json(name="expansion")
    EXPANSION,
    @Json(name="masters")
    MASTERS,
    @Json(name="masterpiece")
    MASTERPIECE,
    @Json(name="from_the_vault")
    FROM_THE_VAULT,
    @Json(name="spellbook")
    SPELLBOOK,
    @Json(name="premium_deck")
    PREMIUM_DECK,
    @Json(name="duel_deck")
    DUEL_DECK,
    @Json(name="draft_innovation")
    DRAFT_INNOVATION,
    @Json(name="treasure_chest")
    TREASURE_CHEST,
    @Json(name="commander")
    COMMANDER,
    @Json(name="planechase")
    PLANECHASE,
    @Json(name="archenemy")
    ARCHENEMY,
    @Json(name="vanguard")
    VANGUARD,
    @Json(name="funny")
    FUNNY,
    @Json(name="starter")
    STARTER,
    @Json(name="box")
    BOX,
    @Json(name="promo")
    PROMO,
    @Json(name="token")
    TOKEN,
    @Json(name="memorabilia")
    MEMORABILIA
}