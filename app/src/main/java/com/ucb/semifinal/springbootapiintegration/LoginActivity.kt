package com.ucb.semifinal.springbootapiintegration

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.ucb.semifinal.springbootapiintegration.databinding.ActivityLoginBinding
import com.ucb.semifinal.springbootapiintegration.models.ApiResponse
import com.ucb.semifinal.springbootapiintegration.models.LoginRequest
import com.ucb.semifinal.springbootapiintegration.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signInButton.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val loginRequest = LoginRequest(email, password)
            loginAccount(loginRequest)
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun loginAccount(credentials: LoginRequest) {
        RetrofitClient.apiService.loginAccount(credentials).enqueue(object: Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    binding.loginStatus.text = response.body()?.Message
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = errorBody?.let {
                        Gson().fromJson(it, ApiResponse::class.java)
                    }
                    binding.loginStatus.text = errorResponse?.Message ?: "Unknown error"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                t.message?.let { Log.e("Response", it) }
                binding.loginStatus.text = "Request failed: ${t.message}"
            }
        })
    }

}