package com.ucb.semifinal.springbootapiintegration

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.ucb.semifinal.springbootapiintegration.databinding.ActivityInventoryCreateBinding
import com.ucb.semifinal.springbootapiintegration.models.ApiResponse
import com.ucb.semifinal.springbootapiintegration.models.Inventory
import com.ucb.semifinal.springbootapiintegration.models.LoginRequest
import com.ucb.semifinal.springbootapiintegration.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InventoryCreate : AppCompatActivity() {
    private lateinit var binding:ActivityInventoryCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInventoryCreateBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.addButton.setOnClickListener {
            val userEmail = binding.inputEmail.text.toString()
            val gallons = binding.inputGallons.text.toString()
            val inventory = Inventory(userEmail, gallons)
            createInventory(inventory)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun createInventory(inventory: Inventory) {
        RetrofitClient.apiService.createInventory(inventory).enqueue(object: Callback<Inventory> {
            override fun onResponse(call: Call<Inventory>, response: Response<Inventory>) {
                if (response.isSuccessful) {
                    binding.status.text = "${ response.body()?.userEmail } created successfully"
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = errorBody?.let {
                        Gson().fromJson(it, ApiResponse::class.java)
                    }
                    binding.status.text = "Unknown error"
                }
            }

            override fun onFailure(call: Call<Inventory>, t: Throwable) {
                t.message?.let { Log.e("Response", it) }
                binding.status.text = "Inventory add Failed"
            }
        })
    }
}