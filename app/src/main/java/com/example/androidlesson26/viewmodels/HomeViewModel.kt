package com.example.androidlesson26.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlesson26.models.responses.get.NamazTime
import com.example.androidlesson26.source.api.Resource
import com.example.androidlesson26.source.api.repositories.NamazTimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: NamazTimeRepository,
) : ViewModel() {

    private val _times = MutableLiveData<List<NamazTime>>()
    val times: LiveData<List<NamazTime>> = _times


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error



    init {
        _loading.value = false
        _error.value = null

    }

    fun getAllNamazTimeByRegion(city:String) {
        _loading.value = true
        viewModelScope.launch() {
            val response = repo.getAllNamazTimeByRegion(city)

                when (response) {
                    is Resource.Success -> {
                        _loading.value = false
                        val itemResponse = response.data
                        if (itemResponse != null) {
                            _times.value = itemResponse.namazTime
                            Log.i("Namaztime: ",  _times.value.toString())
                        } else {
                            _error.value = "No cities found"
                            Log.e("APIFailed",_error.value.toString())

                        }
                    }
                    is Resource.Error -> {
                        _loading.value = false
                       // _error.value = "Failed to fetch cities: ${response.message}"
                        Log.e("APIFailed",_error.value.toString())
                    }
                }

        }
    }







}