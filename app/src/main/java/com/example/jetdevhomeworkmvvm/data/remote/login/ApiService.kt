package com.example.jetdevhomeworkmvvm.data.remote.login

import com.example.jetdevhomeworkmvvm.data.remote.login.pojo.LoginModel
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/login")
    suspend fun getUsers(
        @Query("username") username: String?,
        @Query("password") password: String?
    ): Response<LoginModel>
}