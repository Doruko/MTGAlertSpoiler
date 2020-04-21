package me.alejandro.mtgspoileralert.injection.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import me.alejandro.mtgspoileralert.network.ScryfallApi
import me.alejandro.mtgspoileralert.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@Suppress("unused")
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideScryfallApi(retrofit: Retrofit): ScryfallApi =
        retrofit.create(ScryfallApi::class.java)

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}