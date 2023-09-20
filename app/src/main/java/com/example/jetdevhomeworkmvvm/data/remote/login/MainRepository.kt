package com.example.jetdevhomeworkmvvm.data.remote.login

class MainRepository (private val apiHelper: ApiHelper) {

    suspend fun getUsers(userName: String, psw: String) =  apiHelper.getUsers(userName,psw)

}