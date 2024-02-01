package com.tatara.actorsapp.repository

import com.tatara.actorsapp.retrofit.Character
import com.tatara.actorsapp.retrofit.CharacterApi

class CharacterRepo(private val characterApi: CharacterApi) {
    suspend fun getCharacters(): List<Character> {
        return characterApi.getCharacters()
    }
}