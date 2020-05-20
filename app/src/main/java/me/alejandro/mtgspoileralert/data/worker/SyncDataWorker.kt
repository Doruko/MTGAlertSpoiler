package me.alejandro.mtgspoileralert.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.alejandro.mtgspoileralert.data.usecases.GetCardsUseCase
import me.alejandro.mtgspoileralert.domain.base.Failure
import me.alejandro.mtgspoileralert.domain.model.card.Card
import javax.inject.Inject
import javax.inject.Provider

class SyncDataWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    val getCardsUseCase: GetCardsUseCase
) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val params = GetCardsUseCase.Params(code = "iko")
            getCardsUseCase.invoke(this, params) {
                it.fold(::handleFailure, ::handleSuccess)
            }
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private fun handleFailure(failure: Failure) {

    }

    private fun handleSuccess(list: List<Card>) {

    }

    class Factory @Inject constructor(
        private val useCase: Provider<GetCardsUseCase>
    ) : DataWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): SyncDataWorker =
            SyncDataWorker(appContext, params, useCase.get())
    }

}