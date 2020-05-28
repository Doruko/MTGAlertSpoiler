package me.alejandro.mtgspoileralert.injection.module

import androidx.work.ListenableWorker
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import me.alejandro.mtgspoileralert.data.worker.DataWorkerFactory
import me.alejandro.mtgspoileralert.data.worker.SyncDataWorker
import kotlin.reflect.KClass

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(SyncDataWorker::class)
    fun bindSyncDataWorker(factory: SyncDataWorker.Factory): DataWorkerFactory
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)