package me.alejandro.mtgspoileralert.injection.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import me.alejandro.mtgspoileralert.data.network.ScryfallApi
import me.alejandro.mtgspoileralert.data.repository.CardsRepository
import me.alejandro.mtgspoileralert.data.repository.SetsRepository
import me.alejandro.mtgspoileralert.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@Suppress("unused")
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideCardsRepository(scryfallApi: ScryfallApi): CardsRepository =
        CardsRepository(scryfallApi)

    @Provides
    @Singleton
    internal fun provideSetsRepository(scryfallApi: ScryfallApi): SetsRepository =
        SetsRepository(scryfallApi)

    @Provides
    @Reusable
    internal fun provideScryfallApi(retrofit: Retrofit): ScryfallApi =
        retrofit.create(ScryfallApi::class.java)

    @Provides
    @Reusable
    internal fun provideRetrofitInterface(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}