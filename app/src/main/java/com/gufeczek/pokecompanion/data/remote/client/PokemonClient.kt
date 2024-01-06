package com.gufeczek.pokecompanion.data.remote.client

import com.gufeczek.pokecompanion.data.model.PagedResponse
import com.gufeczek.pokecompanion.data.remote.response.PokemonInfoResponse
import com.gufeczek.pokecompanion.data.remote.response.PokemonResponse
import com.gufeczek.pokecompanion.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonClient {
    @GET("pokemon")
    suspend fun getPokemonPageResponse(
        @Query("limit") limit: Int = Constants.PAGE_SIZE,
        @Query("offset") offset: Int,
    ): Response<PagedResponse<PokemonResponse>>

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: Int
    ): Response<PokemonInfoResponse>
}