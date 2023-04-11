package com.fcadev.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fcadev.rickandmortyapp.model.location.RamLocation
import com.fcadev.rickandmortyapp.model.location.Result
import com.fcadev.rickandmortyapp.service.location.LocationAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CharacterListViewModel : ViewModel() {

    private val locationAPIService = LocationAPIService()
    private val disposable = CompositeDisposable()

    val locations = MutableLiveData<MutableList<Result>>(mutableListOf()) // burada boş bir liste ile başlatılıyor

    fun downloadLocationData(){
        getLocationDataFromAPI()
    }

    private fun getLocationDataFromAPI(){
        disposable.add(
            locationAPIService.getLocationsData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RamLocation>(){
                    override fun onSuccess(t: RamLocation) {
                        locations.value = t.results as MutableList<Result>?
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }

}
