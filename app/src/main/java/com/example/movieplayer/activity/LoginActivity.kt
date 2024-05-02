package com.example.movieplayer.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieplayer.R
import com.example.movieplayer.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginBtn.setOnClickListener {
            if (binding.editTextUsername.text.toString().isEmpty() || binding.editTextPassword.text.toString().isEmpty()){
                Toast.makeText(this,"Please fill your user and password",Toast.LENGTH_SHORT).show()
            }else if (binding.editTextUsername.text.toString().equals("martinLeo") && binding.editTextPassword.text.toString().equals("admin")) {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}