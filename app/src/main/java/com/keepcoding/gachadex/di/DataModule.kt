package com.keepcoding.gachadex.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.keepcoding.gachadex.data.local.LocalDataSource
import com.keepcoding.gachadex.data.local.LocalDataSourceImpl
import com.keepcoding.gachadex.data.remote.RemoteDataSource
import com.keepcoding.gachadex.data.remote.RemoteDataSourceImpl
import com.keepcoding.gachadex.data.PokemonRepository
import com.keepcoding.gachadex.data.PokemonRepositoryImpl
import com.keepcoding.gachadex.data.SettingsRepository
import com.keepcoding.gachadex.data.SettingsRepositoryImpl
import com.keepcoding.gachadex.data.local.PokedexDatabase
import com.keepcoding.gachadex.data.remote.PokemonAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val baseUrl = "https://pokeapi.co/api/v2/"

val dataModule = module {
    single{
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT ).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    single<Retrofit>{
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single<Moshi>{
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    single<PokemonRepository>{ PokemonRepositoryImpl(get(), get()) }
    single<LocalDataSource>{ LocalDataSourceImpl(get()) }
    single<RemoteDataSource>{ RemoteDataSourceImpl(get()) }
    single<SettingsRepository>{SettingsRepositoryImpl(get())}

    single{ getPokemonAPI(get()) }
    single{ getDatabase(get()) }
    single{ providePokemonDAO(get()) }
}

private fun getPokemonAPI(retrofit: Retrofit): PokemonAPI = retrofit.create(PokemonAPI::class.java)
private fun getDatabase(context: Context): PokedexDatabase= Room.databaseBuilder(
                                                                    context,
                                                                    PokedexDatabase::class.java, "pokedex-db"
                                                            ).build()
private fun providePokemonDAO(db: PokedexDatabase) = db.pokemonDAO()