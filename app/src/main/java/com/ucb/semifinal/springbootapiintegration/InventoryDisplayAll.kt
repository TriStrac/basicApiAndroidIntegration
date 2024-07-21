package com.ucb.semifinal.springbootapiintegration

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ucb.semifinal.springbootapiintegration.adapter.CustomListAdapterInventory
import com.ucb.semifinal.springbootapiintegration.databinding.ActivityInventoryDisplayAllBinding
import com.ucb.semifinal.springbootapiintegration.models.Inventory
import com.ucb.semifinal.springbootapiintegration.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InventoryDisplayAll : AppCompatActivity() {
    private lateinit var binding:ActivityInventoryDisplayAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInventoryDisplayAllBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchAllInventory()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    private fun fetchAllInventory() {
        RetrofitClient.apiService.getAllInventory().enqueue(object : Callback<List<Inventory>> {
            override fun onResponse(call: Call<List<Inventory>>, response: Response<List<Inventory>>) {
                if (response.isSuccessful) {
                    val accounts = response.body()
                    if (accounts != null) {
                        updateListView(accounts)
                    } else {
                        showToast("No accounts found")
                    }
                } else {
                    showToast("Failed to fetch Inventory: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<Inventory>>, t: Throwable) {
                showToast("Request failed: ${t.message}")
            }
        })
    }

    private fun updateListView(accounts: List<Inventory>) {
        val adapter = CustomListAdapterInventory(this, accounts)
        binding.simpleListView.adapter = adapter
    }
}