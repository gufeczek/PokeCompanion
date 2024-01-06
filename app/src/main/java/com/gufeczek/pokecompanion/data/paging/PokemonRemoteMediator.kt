package com.gufeczek.pokecompanion.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.gufeczek.pokecompanion.data.local.PokeCompanionDatabase
import com.gufeczek.pokecompanion.data.local.entity.PokemonEntity
import com.gufeczek.pokecompanion.data.local.entity.PokemonRemoteKey
import com.gufeczek.pokecompanion.data.remote.source.PokemonRemoteDataSource
import com.gufeczek.pokecompanion.util.Constants
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PokemonRemoteMediator(
    private val db: PokeCompanionDatabase,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
) : RemoteMediator<Int, PokemonEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextOffset
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            val response = pokemonRemoteDataSource.getPokemonPage(offset = offset)
            val results = response?.results ?: emptyList()

            val nextOffset = response?.nextOffset ?: 0

            db.withTransaction {
                db.pokemonDao().insertAll(results.map { it.asEntity() })
                val keys = results.map { pokemon ->
                    PokemonRemoteKey(
                        id = pokemon.id,
                        nextOffset = nextOffset
                    )
                }
                db.pokemonRemoteKeyDao().insertAll(keys)
            }

            return MediatorResult.Success(endOfPaginationReached = results.size < Constants.PAGE_SIZE)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonEntity>): PokemonRemoteKey? {
        return state.pages.lastOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { pokemon ->
            db.pokemonRemoteKeyDao().selectById(pokemon.id)
        }
    }
}