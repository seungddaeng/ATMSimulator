package com.example.atmsimulator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.atmsimulator.databinding.ActivityAtmBinding
import com.example.atmsimulator.databinding.ActivityDepositoBinding

class DepositoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDepositoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepositoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.otroDepositoBoton.setOnClickListener {
            val intent = Intent(this, OtroDepositoActivity::class.java)
            startActivity(intent)
        }

    }
}