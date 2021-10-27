package com.picpay.desafio.android.data

import javax.inject.Inject

class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}