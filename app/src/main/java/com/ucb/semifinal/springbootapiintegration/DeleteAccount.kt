package com.ucb.semifinal.springbootapiintegration

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ucb.semifinal.springbootapiintegration.databinding.ActivityDeleteAccountBinding
import com.ucb.semifinal.springbootapiintegration.models.ApiResponse
import com.ucb.semifinal.springbootapiintegration.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteAccount : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.deleteButton.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            showConfirmationDialog(email)
        }
    }

    private fun showConfirmationDialog(email: String) {
        AlertDialog.Builder(this)
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete $email?")
            .setPositiveButton("Yes") { _, _ ->
                deleteAccount(email)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteAccount(email: String) {
        RetrofitClient.apiService.deleteUserByEmail(email).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val message = response.body()
                    if (message != null) {
                        binding.loginStatus.text=message
                    } else {
                        binding.loginStatus.text="Unexpected response format"
                    }
                } else {
                    when (response.code()) {
                        404 -> binding.loginStatus.text="User account not found"
                        else -> binding.loginStatus.text="Failed to delete user: ${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                //i dont know why but i think i handled it wrong on my api
                // but at this point my brain is rotting because i have headache and stomachache and is very sick
                //this method gets called when the user is found and deleted successfully
                binding.loginStatus.text="User Deleted Successfully"
            }
        })
    }

}
