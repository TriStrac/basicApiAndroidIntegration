package com.ucb.semifinal.springbootapiintegration

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.ucb.semifinal.springbootapiintegration.databinding.ActivitySignUpBinding
import com.ucb.semifinal.springbootapiintegration.models.ApiResponse
import com.ucb.semifinal.springbootapiintegration.models.LoginRequest
import com.ucb.semifinal.springbootapiintegration.models.SignUpRequest
import com.ucb.semifinal.springbootapiintegration.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signUpButton.setOnClickListener {
            val barangay = binding.signUpBarangay.text.toString()
            val birthdate = binding.signUpBirthdate.text.toString()
            val city = binding.signUpCity.text.toString()
            val email = binding.signUpEmail.text.toString()
            val firstName = binding.signUpFirstName.text.toString()
            val lastName = binding.signUpLastName.text.toString()
            val middleName = binding.signUpMiddleName.text.toString()
            val password = binding.signUpPassword.text.toString()
            val province = binding.signUpProvince.text.toString()
            val street = binding.signUpStreet.text.toString()
            val type = binding.signUpType.text.toString()

            val signUpRequest = SignUpRequest(
                barangay = barangay,
                birthdate = birthdate,
                city = city,
                email = email,
                firstName = firstName,
                lastName = lastName,
                middleName = middleName,
                password = password,
                province = province,
                street = street,
                type = type
            )

            signUpAccount(signUpRequest)
        }


        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun signUpAccount(credentials: SignUpRequest) {
        RetrofitClient.apiService.createAccount(credentials).enqueue(object: Callback<ApiResponse> {
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