package com.example.jetdevhomeworkmvvm.ui.activity.base

import androidx.lifecycle.ViewModel
import com.example.jetdevhomeworkmvvm.exts.LOG_TYPE_INFO
import com.example.jetdevhomeworkmvvm.exts.printLog
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
abstract class BaseViewModel : ViewModel(), KoinComponent {

    init {
        javaClass.simpleName.printLog(LOG_TYPE_INFO, "created")
    }

    override fun onCleared() {
        super.onCleared()
        javaClass.simpleName.printLog(LOG_TYPE_INFO, "destroyed")
    }
}