package me.alejandro.mtgspoileralert.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.data.usecases.GetCardsUseCase
import me.alejandro.mtgspoileralert.domain.base.Failure
import me.alejandro.mtgspoileralert.domain.model.card.Card
import me.alejandro.mtgspoileralert.ui.MainActivity
import me.alejandro.mtgspoileralert.utils.*
import javax.inject.Inject
import javax.inject.Provider

class SyncDataWorker @Inject constructor(
    val context: Context,
    params: WorkerParameters,
    private val getCardsUseCase: GetCardsUseCase
) :
    CoroutineWorker(context, params) {



    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val prefs = context.getSharedPreferences(CARDS_PREFERENCE, 0)
            prefs.getString(LATEST_SET_PREFERENCE, null)?.let { latestSet ->
                val params = GetCardsUseCase.Params(code = latestSet)
                getCardsUseCase.invoke(this, params) {
                    it.fold(::handleFailure, ::handleSuccess)
                }
                Result.success()
            }
            Result.failure()

        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private fun handleFailure(failure: Failure) {
        //Ignored
    }

    private fun handleSuccess(list: List<Card>) {
        val prefs = context.getSharedPreferences(CARDS_PREFERENCE, 0)

        prefs.getString(LATEST_RESPONSE_PREFERENCE, null)?.let { latestResponse ->
            val moshi = Moshi.Builder().build()
            val type = Types.newParameterizedType(List::class.java, Card::class.java)
            val adapter = moshi.adapter<List<Card>>(type)

            adapter.fromJson(latestResponse)?.let {
                if (list.size > it.size) {
                    createNotificationChannel()

                    val bundle = Bundle()
                    bundle.putString(SET_CODE_EXTRA, prefs.getString(LATEST_SET_PREFERENCE, ""))

                    val pendingIntent = NavDeepLinkBuilder(context).apply {
                        setComponentName(MainActivity::class.java)
                        setGraph(R.navigation.mobile_navigation)
                        setDestination(R.id.cardsFragment)
                        setArguments(bundle)
                    }.createPendingIntent()


                    val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
                        setSmallIcon(R.mipmap.ic_launcher_round)
                        setContentTitle(context.resources.getString(R.string.new_cards_notification_title))
                        setContentText(context.resources.getString(R.string.new_cards_notification_content))
                        priority = NotificationCompat.PRIORITY_DEFAULT
                        setContentIntent(pendingIntent)
                    }

                    with(NotificationManagerCompat.from(context)) {
                        notify(1, builder.build())
                    }
                }
            }
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