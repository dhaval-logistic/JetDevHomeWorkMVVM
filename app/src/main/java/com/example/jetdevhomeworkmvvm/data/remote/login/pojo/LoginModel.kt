package com.example.jetdevhomeworkmvvm.data.remote.login.pojo

data class LoginModel(
	val data: Data? = null,
	val errorMessage: String? = null,
	val errorCode: String? = null
)

data class Data(
	val isDeleted: Boolean? = null,
	val userName: String? = null,
	val userId: String? = null
)

