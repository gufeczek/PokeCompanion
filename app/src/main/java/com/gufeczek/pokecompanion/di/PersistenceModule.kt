package com.gufeczek.pokecompanion.di

import android.app.Application
import androidx.room.Room
import com.gufeczek.pokecompanion.data.local.PokeCompanionDatabase
import com.gufeczek.pokecompanion.data.local.dao.PokemonDao
import com.gufeczek.pokecompanion.data.local.dao.PokemonRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): PokeCompanionDatabase {
        return Room
            .databaseBuilder(application, PokeCompanionDatabase::class.java, "PokeCompanion.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(db: PokeCompanionDatabase): PokemonDao {
        return db.pokemonDao()
    }

    @Provides
    @Singleton
    fun providePokemonRemoteKeyDao(db: PokeCompanionDatabase): PokemonRemoteKeyDao {
        return db.pokemonRemoteKeyDao()
    }
}