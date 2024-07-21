package com.ucb.semifinal.springbootapiintegration

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ucb.semifinal.springbootapiintegration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.accountSignUp.setOnClickListener {
            nextActivity(SignUpActivity::class.java)
        }

        binding.accountSignIn.setOnClickListener {
            nextActivity(LoginActivity::class.java)
        }

        binding.accountDisplayAccounts.setOnClickListener {
            nextActivity(DisplayAccountsActivity::class.java)
        }

        binding.accountDeleteAccount.setOnClickListener {
            nextActivity(DeleteAccount::class.java)
        }
    }

    private fun nextActivity(target: Class<out AppCompatActivity>) {
        val intent = Intent(this@MainActivity, target)
        startActivity(intent)
    }


}