package com.example.jetdevhomeworkmvvm.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jetdevhomeworkmvvm.R
import com.example.jetdevhomeworkmvvm.databinding.ActivityMainBinding
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private  val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}