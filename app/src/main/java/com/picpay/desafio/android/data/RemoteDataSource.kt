package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.model.User
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val picpayService: PicPayService
) {

    suspend fun getUsers(list: Call<List<User>>): Response<User>{
        return picpayService.getUsers(list)
    }
}