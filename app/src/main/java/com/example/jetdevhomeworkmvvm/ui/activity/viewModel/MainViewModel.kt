package com.example.jetdevhomeworkmvvm.ui.activity.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetdevhomeworkmvvm.data.remote.login.MainRepository
import com.example.jetdevhomeworkmvvm.data.remote.login.pojo.LoginModel
import com.example.jetdevhomeworkmvvm.utils.NetworkHelper
import com.example.jetdevhomeworkmvvm.utils.Resource
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _loginData = MutableLiveData<Resource<LoginModel?>>()
    val loginData: LiveData<Resource<LoginModel?>> get() = _loginData


    private val _tokenData = MutableLiveData<String>()
    val tokenData: LiveData<String> get() = _tokenData

     fun fetchUsers(userName: String, psw: String) {
        viewModelScope.launch {
            _loginData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getUsers(userName,psw).let {
                    if (it.isSuccessful) {
                        _loginData.postValue(Resource.success(it.body()))
                        _tokenData.postValue(it.headers()["X-Acc"])
                    } else _loginData.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _loginData.postValue(Resource.error("No internet connection", null))
        }
    }
}