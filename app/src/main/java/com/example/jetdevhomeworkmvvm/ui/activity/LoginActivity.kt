package com.example.jetdevhomeworkmvvm.ui.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.jetdevhomeworkmvvm.data.remote.login.local.demo.Demo
import com.example.jetdevhomeworkmvvm.data.remote.login.local.demo.DemoDatabase
import com.example.jetdevhomeworkmvvm.databinding.ActivityLoginBinding
import com.example.jetdevhomeworkmvvm.exts.beGone
import com.example.jetdevhomeworkmvvm.exts.beVisible
import com.example.jetdevhomeworkmvvm.exts.click
import com.example.jetdevhomeworkmvvm.exts.showToast
import com.example.jetdevhomeworkmvvm.ui.activity.viewModel.MainViewModel
import com.example.jetdevhomeworkmvvm.utils.SessionManager
import com.example.jetdevhomeworkmvvm.utils.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by viewModel()
    private var sessionManager: SessionManager? = null
    private  var userName=""
    private  var userId=""
    private  var userToken=""
    lateinit var db: DemoDatabase
    lateinit var dao: Demo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inItView()
        clickView()
        observer()

    }

    private fun inItView() {
        sessionManager = SessionManager(applicationContext)



    }

    private fun observer() {
        binding.apply {

            mainViewModel.tokenData.observe(this@LoginActivity,Observer{
                userToken=it.toString()
                lifecycleScope.launch{
                    if (appCompatCheckBox.isChecked){
                        db = DemoDatabase.getInstance(this@LoginActivity)
                        val user = Demo(name = userName,token=userToken)
                        db.demoDao().insertDemo(user)
                    }
                    startActivity(
                        Intent(this@LoginActivity, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                    finish()
                    delay(1000)
                }
            })

            mainViewModel.loginData.observe(this@LoginActivity, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.data?.let {it->
                            userId= it.userId.toString()
                            userName= it.userName.toString()
                        }
                        pbLoading.beGone()
                    }
                    Status.LOADING -> {
                        pbLoading.beVisible()
                    }

                    Status.ERROR -> {
                        it.message?.showToast(this@LoginActivity)
                        pbLoading.beGone()
                    }
                }
            })

        }
    }

    private fun clickView() {
        binding.apply {

            btnLogin.click {
                if (etEmail.text.isNullOrBlank()) {
                    "Please Enter Email".showToast(this@LoginActivity)
                } else if (etEmail.text.isNullOrBlank()) {
                    "Please Enter Password".showToast(this@LoginActivity)
                } else {
                    callLoginApi()
                }

            }
        }
    }

    private fun callLoginApi() {
        binding.apply {
            pbLoading.beVisible()
            mainViewModel.fetchUsers(etEmail.text.toString(), etPassword.text.toString())
        }
    }

}