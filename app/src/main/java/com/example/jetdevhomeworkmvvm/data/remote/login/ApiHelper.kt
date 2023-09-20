package com.example.jetdevhomeworkmvvm.data.remote.login

import com.example.jetdevhomeworkmvvm.data.remote.login.pojo.LoginModel
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(userName: String, psw: String): Response<LoginModel>

}