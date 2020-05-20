package me.alejandro.mtgspoileralert.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.data.usecases.GetCardsUseCase
import me.alejandro.mtgspoileralert.domain.base.Failure
import me.alejandro.mtgspoileralert.domain.model.card.Card
import javax.inject.Inject
import javax.inject.Provider

class SyncDataWorker @Inject constructor(
    val context: Context,
    params: WorkerParameters,
    private val getCardsUseCase: GetCardsUseCase
) :
    CoroutineWorker(context, params) {

    val CHANNEL_ID = "cardsId"

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
        //Ignored
    }

    private fun handleSuccess(list: List<Card>) {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(context.resources.getString(R.string.new_cards_notification_title))
            .setContentText(context.resources.getString(R.string.new_cards_notification_content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "New cards notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)

            val notificationManager: NotificationManager =
                getSystemService(context, NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    class Factory @Inject constructor(
        private val useCase: Provider<GetCardsUseCase>
    ) : DataWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): SyncDataWorker =
            SyncDataWorker(appContext, params, useCase.get())
    }

}