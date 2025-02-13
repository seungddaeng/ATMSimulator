package com.example.atmsimulator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.atmsimulator.databinding.ActivityDepositoBinding
import com.example.atmsimulator.databinding.ActivityRetiroBinding

class RetiroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRetiroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetiroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.otroRetiroBoton.setOnClickListener {
            val intent = Intent(this, OtroRetiroActivity::class.java)
            startActivity(intent)
        }

    }
}