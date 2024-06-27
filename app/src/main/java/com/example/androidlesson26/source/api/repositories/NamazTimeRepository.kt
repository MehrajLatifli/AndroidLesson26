package com.example.androidlesson26.source.api.repositories

import android.util.Log
import com.example.androidlesson26.models.responses.get.NamazTimeResponse
import com.example.androidlesson26.source.api.Resource
import com.example.androidlesson26.source.api.IApiManager
import com.example.androidlesson26.utilities.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NamazTimeRepository @Inject constructor(private val api: IApiManager) {

    suspend fun getAllNamazTimeByRegion(city:String): Resource<NamazTimeResponse> {
        return safeApiRequest {
            api.getAllNamazTimeByRegion(city)
        }
    }







    suspend fun <T> safeApiRequest(request: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {

                val response = request.invoke()

                if (response.isSuccessful) {
                    Resource.Success(response.body())
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Log.e("APIFailed",e.message.toString())
                Resource.Error(e.localizedMessage)
            }
        }
    }
}