package com.ucb.semifinal.springbootapiintegration

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.ucb.semifinal.springbootapiintegration.databinding.ActivitySubscriptionCreateBinding
import com.ucb.semifinal.springbootapiintegration.models.ApiResponse
import com.ucb.semifinal.springbootapiintegration.models.Inventory
import com.ucb.semifinal.springbootapiintegration.models.Subscription
import com.ucb.semifinal.springbootapiintegration.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubscriptionCreate : AppCompatActivity() {
    private lateinit var binding: ActivitySubscriptionCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySubscriptionCreateBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.addButton.setOnClickListener {
            val customerEmail = binding.inputEmail.text.toString()
            val stattionEmail = binding.inputEmail2.text.toString()
            val subscription = Subscription(customerEmail, stattionEmail)
            createSubscription(subscription)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun createSubscription(subscription: Subscription) {
        RetrofitClient.apiService.createSubscription(subscription).enqueue(object: Callback<Subscription> {
            override fun onResponse(call: Call<Subscription>, response: Response<Subscription>) {
                if (response.isSuccessful) {
                    binding.status.text = "${ response.body()?.customerEmail } created successfully"
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = errorBody?.let {
                        Gson().fromJson(it, ApiResponse::class.java)
                    }
                    binding.status.text = "Unknown error"
                }
            }

            override fun onFailure(call: Call<Subscription>, t: Throwable) {
                t.message?.let { Log.e("Response", it) }
                binding.status.text = "Inventory add Failed"
            }
        })
    }
}