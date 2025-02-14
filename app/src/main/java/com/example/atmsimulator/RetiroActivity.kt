package com.example.atmsimulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atmsimulator.databinding.ActivityRetiroBinding

class RetiroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetiroBinding
    private var saldo = 0.0
    private var pin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetiroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pin = intent.getStringExtra("PIN") ?: ""
        saldo = intent.getDoubleExtra("SALDO", 0.0)

        binding.btn10.setOnClickListener { realizarRetiro(10.0) }
        binding.btn20.setOnClickListener { realizarRetiro(20.0) }
        binding.btn50.setOnClickListener { realizarRetiro(50.0) }
        binding.btn100.setOnClickListener { realizarRetiro(100.0) }
        binding.btn200.setOnClickListener { realizarRetiro(200.0) }
        binding.btn500.setOnClickListener { realizarRetiro(500.0) }

        binding.btnOtro.setOnClickListener {
            val intent = Intent(this, OtroRetiroActivity::class.java).apply {
                putExtra("PIN", pin)
                putExtra("SALDO", saldo)
            }
            startActivity(intent)
        }

        binding.btnRegresarRetiro.setOnClickListener {
            regresarAlATM()
        }
    }

    private fun realizarRetiro(monto: Double) {
        if (monto > 0 && saldo >= monto) {
            val saldoAnterior = saldo
            saldo -= monto
            guardarSaldo()

            val intent = Intent(this, ComprobanteActivity::class.java).apply {
                putExtra("PIN", pin)
                putExtra("SALDO_ANTERIOR", saldoAnterior)
                putExtra("MONTO", monto)
                putExtra("SALDO_NUEVO", saldo)
                putExtra("TIPO", "Retiro")
            }
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarSaldo() {
        val sharedPref = getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putFloat(pin, saldo.toFloat())
            apply()
        }
    }

    private fun regresarAlATM() {
        val intent = Intent(this, ATMActivity::class.java).apply {
            putExtra("PIN", pin)
            putExtra("SALDO", saldo)
        }
        startActivity(intent)
        finish()
    }
}
