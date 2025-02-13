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

        // Obtener PIN y saldo del intent
        pin = intent.getStringExtra("PIN") ?: ""
        saldo = intent.getDoubleExtra("SALDO", 0.0)

        // Configurar listeners para los botones de retiro
        binding.btn10.setOnClickListener { realizarRetiro(10.0) }
        binding.btn20.setOnClickListener { realizarRetiro(20.0) }
        binding.btn50.setOnClickListener { realizarRetiro(50.0) }
        binding.btn100.setOnClickListener { realizarRetiro(100.0) }
        binding.btn200.setOnClickListener { realizarRetiro(200.0) }
        binding.btn500.setOnClickListener { realizarRetiro(500.0) }

        binding.btnOtro.setOnClickListener {
            val intent = Intent(this, OtroRetiroActivity::class.java)
            intent.putExtra("PIN", pin)
            intent.putExtra("SALDO", saldo)
            startActivity(intent)
        }

        binding.btnRegresarRetiro.setOnClickListener {
            regresarAlATM()
        }
    }


    private fun realizarRetiro(monto: Double) {
        if (monto > 0) {
            if (saldo >= monto) {
                saldo -= monto
                guardarSaldo()
                Toast.makeText(this, "Retiro exitoso: $${"%.2f".format(monto)}", Toast.LENGTH_SHORT).show()
                regresarAlATM()
            } else {
                Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
            }
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