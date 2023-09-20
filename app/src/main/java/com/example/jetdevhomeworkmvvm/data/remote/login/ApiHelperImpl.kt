package com.example.jetdevhomeworkmvvm.data.remote.login

import com.example.jetdevhomeworkmvvm.data.remote.login.pojo.LoginModel
import retrofit2.Response

class ApiHelperImpl (private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(userName: String, psw: String): Response<LoginModel> = apiService.getUsers(userName,psw)

}