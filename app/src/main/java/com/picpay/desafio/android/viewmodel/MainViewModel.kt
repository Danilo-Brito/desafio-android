package com.picpay.desafio.android.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.Repository
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class MainViewModel constructor (
    private val repository: Repository,
    application: Application
): AndroidViewModel(application){


    /** RETROFIT**/
    var usersResponse: MutableLiveData<NetworkResult<User>> = MutableLiveData()

    fun getUsers(list: Call<List<User>>) = viewModelScope.launch {
        getUsersSafeCall(list)
    }

    private suspend fun getUsersSafeCall(list: Call<List<User>>) {
        usersResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getUsers(list)
                usersResponse.value = handleUserResponse(response)

                val users = usersResponse.value!!.data
                if (users != null){
                    offilineCacheUsers(users)
                }
            } catch (e: Exception) {
                usersResponse.value = NetworkResult.Error("Users not found.")
            }
        } else {
            usersResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offilineCacheUsers(users: User) {
        //Todo
    }

    private fun handleUserResponse(response: Response<User>): NetworkResult<User>? {
        return when {
            response.code() == 500 -> {
                NetworkResult.Error("Internal Server Error.")
            }
            response.isSuccessful ->{
                val users = response.body()
                NetworkResult.Success(users!!)
            }else -> {
                NetworkResult.Error(response.message())
            }
        }
    }


    private fun hasInternetConnection (): Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}