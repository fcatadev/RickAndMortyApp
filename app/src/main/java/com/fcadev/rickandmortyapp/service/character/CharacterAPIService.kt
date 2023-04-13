package com.fcadev.rickandmortyapp.service.character

import androidx.lifecycle.MutableLiveData
import com.fcadev.rickandmortyapp.model.character.RamCharacter
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CharacterAPIService {

    //https://rickandmortyapi.com/api/character

    private val BASE_URL = "https://rickandmortyapi.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CharacterAPI::class.java)

    fun getCharacterData(): Single<RamCharacter>{
        return api.getCharacters()
    }

    fun getCharactersByIds(ids: MutableLiveData<ArrayList<String>>): Single<RamCharacter> {
        return api.getCharactersByIds(ids)
    }
}
