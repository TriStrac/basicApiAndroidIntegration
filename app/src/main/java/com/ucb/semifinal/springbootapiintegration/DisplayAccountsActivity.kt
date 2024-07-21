package com.ucb.semifinal.springbootapiintegration

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ucb.semifinal.springbootapiintegration.adapter.CustomListAdapter
import com.ucb.semifinal.springbootapiintegration.databinding.ActivityDisplayAccountsBinding
import com.ucb.semifinal.springbootapiintegration.network.RetrofitClient
import com.ucb.semifinal.springbootapiintegration.models.SignUpRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisplayAccountsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisplayAccountsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayAccountsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchAllAccounts()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    private fun fetchAllAccounts() {
        RetrofitClient.apiService.getAllAccounts().enqueue(object : Callback<List<SignUpRequest>> {
            override fun onResponse(call: Call<List<SignUpRequest>>, response: Response<List<SignUpRequest>>) {
                if (response.isSuccessful) {
                    val accounts = response.body()
                    if (accounts != null) {
                        updateListView(accounts)
                    } else {
                        showToast("No accounts found")
                    }
                } else {
                    showToast("Failed to fetch accounts: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<SignUpRequest>>, t: Throwable) {
                showToast("Request failed: ${t.message}")
            }
        })
    }

    private fun updateListView(accounts: List<SignUpRequest>) {
        val adapter = CustomListAdapter(this, accounts)
        binding.simpleListView.adapter = adapter
    }
}
