package com.example.jetdevhomeworkmvvm.data.remote.login.local.demo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetdevhomeworkmvvm.data.remote.login.local.demo.Demo
import com.example.jetdevhomeworkmvvm.data.remote.login.local.demo.DemoDao

@Database(entities = [Demo::class], version = 2)
abstract class DemoDatabase : RoomDatabase() {
    abstract  fun  demoDao(): DemoDao

    companion object {
        @Volatile
        private var instance: DemoDatabase? = null;
        fun getInstance(context: Context): DemoDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context,DemoDatabase::class.java,"user_db")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance as DemoDatabase
        }
    }

}



