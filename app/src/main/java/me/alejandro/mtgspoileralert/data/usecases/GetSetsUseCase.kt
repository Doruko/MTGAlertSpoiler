package me.alejandro.mtgspoileralert.data.usecases

import me.alejandro.mtgspoileralert.data.repository.SetsRepository
import me.alejandro.mtgspoileralert.domain.base.BaseUseCase
import me.alejandro.mtgspoileralert.domain.base.Either
import me.alejandro.mtgspoileralert.domain.base.Failure
import me.alejandro.mtgspoileralert.domain.model.set.Set
import javax.inject.Inject

class GetSetsUseCase @Inject constructor(private val repository: SetsRepository) :
    BaseUseCase<List<Set>, GetSetsUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Set>> {
        return try {
            val response = repository.getSets()
            Either.Right(response.data)
        } catch (e: Exception) {
            Either.Left(SetFailure(e))
        }
    }

    class Params

    data class SetFailure(val error: Exception) : Failure.FeatureFailure(error)

}