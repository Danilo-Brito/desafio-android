package com.picpay.desafio.android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.picpay.desafio.android.data.Repository

class MainViewModel (
    private val repository: Repository,
    application: Application
): AndroidViewModel(application){

}