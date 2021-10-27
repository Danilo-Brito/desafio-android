package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface PicPayService {

    @GET("users")
    suspend fun getUsers(
        list: Call<List<User>>
    ): Response<User>
}