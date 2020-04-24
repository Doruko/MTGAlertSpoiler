package me.alejandro.mtgspoileralert.data.usecases

import me.alejandro.mtgspoileralert.data.repository.CardsRepository
import me.alejandro.mtgspoileralert.domain.base.BaseUseCase
import me.alejandro.mtgspoileralert.domain.base.Either
import me.alejandro.mtgspoileralert.domain.base.Failure
import me.alejandro.mtgspoileralert.domain.model.card.Card
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val repository: CardsRepository) :
    BaseUseCase<List<Card>, GetCardsUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Card>> {
        return try {
            val response = repository.getCards(params.set, params.code, params.unique, params.order)
            Either.Right(response.data)
        } catch (e: Exception) {
            Either.Left(SetFailure(e))
        }
    }

    data class Params(
        val set: String = "set",
        val code: String,
        val unique: String = "prints",
        val order: String = "spoiled"
    )

    data class SetFailure(val error: Exception) : Failure.FeatureFailure(error)

}