package com.tatara.actorsapp.retrofit

import com.tatara.actorsapp.retrofit.Character
import retrofit2.http.GET

interface CharacterApi {
    @GET("api/characters")
    suspend fun getCharacters(): List<Character>
}