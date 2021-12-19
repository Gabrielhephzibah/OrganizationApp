package com.cherish.homeprojectapp.data.repository


import com.cherish.homeprojectapp.data.remote.ApiService
import com.cherish.homeprojectapp.data.remote.IODispatcher
import com.cherish.homeprojectapp.data.remote.ResponseManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton


class DataRepository@Inject constructor(private val apiService: ApiService, @IODispatcher private val ioDispatcher: CoroutineDispatcher) {
    fun getOrganization() = flow {
            val response = apiService.getOrganizations()
            emit(ResponseManager.Success(response))
            emit(ResponseManager.Loading(false))
        }.flowOn(ioDispatcher)
            .catch { emit(ResponseManager.Failure(it)) }
            .onStart { emit(ResponseManager.Loading(true)) }
            .onCompletion { emit(ResponseManager.Loading(false)) }
}