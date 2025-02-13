package com.example.atmsimulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atmsimulator.databinding.ActivityDepositoBinding

class DepositoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDepositoBinding
    private var saldo = 0.0
    private var pin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepositoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener PIN y saldo del intent
        pin = intent.getStringExtra("PIN") ?: ""
        saldo = intent.getDoubleExtra("SALDO", 0.0)

        binding.btn10.setOnClickListener { realizarDeposito(10.0) }
        binding.btn20.setOnClickListener { realizarDeposito(20.0) }
        binding.btn50.setOnClickListener { realizarDeposito(50.0) }
        binding.btn100.setOnClickListener { realizarDeposito(100.0) }
        binding.btn200.setOnClickListener { realizarDeposito(200.0) }
        binding.btn500.setOnClickListener { realizarDeposito(500.0) }

        binding.btnOtro.setOnClickListener {
            val intent = Intent(this, OtroDepositoActivity::class.java)
            intent.putExtra("PIN", pin)
            intent.putExtra("SALDO", saldo)
            startActivity(intent)
        }

        binding.btnRegresarDeposito.setOnClickListener {
            regresarAlATM()
        }
    }

    private fun realizarDeposito(monto: Double) {
        if (monto > 0) {
            saldo += monto
            guardarSaldo()
            Toast.makeText(this, "Depósito exitoso: $${"%.2f".format(monto)}", Toast.LENGTH_SHORT).show()
            regresarAlATM()
        } else {
            Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarSaldo() {
        val sharedPref = getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putFloat(pin, saldo.toFloat())
        editor.apply()
    }

    private fun regresarAlATM() {
        val intent = Intent(this, ATMActivity::class.java)
        intent.putExtra("PIN", pin)
        intent.putExtra("SALDO", saldo)
        startActivity(intent)
        finish()
    }
}