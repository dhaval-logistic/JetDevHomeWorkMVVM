package com.example.jetdevhomeworkmvvm.data.remote.login.local.demo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DemoDao {

    @Query("SELECT * FROM user_db")
    fun getAllUsers(): Demo

    @Insert
    fun insertDemo(user: Demo)

    @Delete
    fun deleteUser(user: Demo)
}