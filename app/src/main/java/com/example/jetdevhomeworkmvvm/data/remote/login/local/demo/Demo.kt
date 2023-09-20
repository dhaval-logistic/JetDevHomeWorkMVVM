package com.example.jetdevhomeworkmvvm.data.remote.login.local.demo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_db")
data class Demo constructor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val token: String
)
