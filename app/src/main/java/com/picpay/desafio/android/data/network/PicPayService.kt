package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.model.User
import retrofit2.Call
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    fun getUsers(): Call<List<User>>
}