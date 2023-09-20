package com.example.jetdevhomeworkmvvm.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.jetdevhomeworkmvvm.data.remote.login.local.demo.DemoDatabase
import com.example.jetdevhomeworkmvvm.databinding.ActivitySplashBinding
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class SplashActivity : AppCompatActivity() {

    private  val binding : ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    lateinit var db: DemoDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inItView()

    }

    private fun inItView() {

        db = DemoDatabase.getInstance(this)

        val user =  db.demoDao().getAllUsers()

        if (user != null) {
            Handler(mainLooper).postDelayed(Runnable {
                if (!user.token.isNullOrBlank()){
                    startActivity(Intent(this, MainActivity::class.java))
                }else{
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                finish()
            }, 2000)
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }


    }
}