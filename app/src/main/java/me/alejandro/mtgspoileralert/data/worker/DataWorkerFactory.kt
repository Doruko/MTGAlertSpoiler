package me.alejandro.mtgspoileralert.data.worker

import android.content.Context
import androidx.work.WorkerParameters

interface DataWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): SyncDataWorker
}