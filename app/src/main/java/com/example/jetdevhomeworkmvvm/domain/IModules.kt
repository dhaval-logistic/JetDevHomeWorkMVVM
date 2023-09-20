package com.example.jetdevhomeworkmvvm.domain


import android.content.Context
import androidx.room.Room
import com.example.jetdevhomeworkmvvm.data.remote.login.ApiHelper
import com.example.jetdevhomeworkmvvm.data.remote.login.ApiHelperImpl
import com.example.jetdevhomeworkmvvm.data.remote.login.ApiService
import com.example.jetdevhomeworkmvvm.data.remote.login.MainRepository
import com.example.jetdevhomeworkmvvm.ui.activity.viewModel.MainViewModel
import com.example.jetdevhomeworkmvvm.utils.NetworkHelper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



private const val BASE_URL = "http://private-222d3-homework5.apiary-mock.com/"

val netModules = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(),BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }

}


val repoModule = module {

    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }


    single {
        MainRepository(get())
    }
}



val viewModelModule = module {
    viewModel {
        MainViewModel(get(),get())
    }
}


private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


/*private fun provideDatabase(application: Application): DemoDatabase {
    return Room.databaseBuilder(application, DemoDatabase::class.java, "I-Database")
        .fallbackToDestructiveMigration()
        .build()
}

private fun provideDao(database: DemoDatabase): DemoDao {
    return database.demoDao()
}*/

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {

    val commonHeadersInterceptor = Interceptor { chain ->

        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("IMSI", "357175048449937")
            .header("IMEI", "510110406068589")
            .build()

        chain.proceed(request)
    }

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        level = HttpLoggingInterceptor.Level.HEADERS
    }



    OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(commonHeadersInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()


private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()



