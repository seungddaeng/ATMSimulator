package com.example.atmsimulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.atmsimulator.databinding.ActivityAtmBinding

class ATMActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtmBinding
    private var saldo = 0.0
    private var pin = ""
    private var saldoVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pin = intent.getStringExtra("PIN") ?: ""

        saldo = obtenerSaldo(pin).toDouble()

        actualizarSaldo()

        binding.btnMostrarSaldo.setOnClickListener {
            saldoVisible = !saldoVisible
            actualizarSaldo()
        }

        binding.btnDepositar.setOnClickListener {
            val intent = Intent(this, DepositoActivity::class.java)
            intent.putExtra("PIN", pin)
            intent.putExtra("SALDO", saldo)
            startActivity(intent)
        }

        binding.btnRetirar.setOnClickListener {
            val intent = Intent(this, RetiroActivity::class.java)
            intent.putExtra("PIN", pin)
            intent.putExtra("SALDO", saldo)
            startActivity(intent)
        }

        binding.btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun actualizarSaldo() {
        if (saldoVisible) {
            binding.tvSaldo.text = "Saldo: $${"%.2f".format(saldo)}"
            binding.btnMostrarSaldo.setImageResource(R.drawable.ojo)
        } else {
            binding.tvSaldo.text = "Saldo: ****"
            binding.btnMostrarSaldo.setImageResource(R.drawable.ojo_cerrado)
        }
    }

    private fun obtenerSaldo(pin: String): Float {
        val sharedPref = getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)
        return sharedPref.getFloat(pin, 0.0f)
    }

    private fun cerrarSesion() {
        val intent = Intent(this, IniciarSesionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}