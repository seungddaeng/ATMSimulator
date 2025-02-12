package com.example.atmsimulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atmsimulator.databinding.ActivityAtmBinding

class ATMActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtmBinding
    private var saldo = 0.0
    private var pin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener PIN y saldo del intent
        pin = intent.getStringExtra("PIN") ?: ""
        saldo = intent.getDoubleExtra("SALDO", 0.0)

        actualizarSaldo()

        binding.btnDepositar.setOnClickListener {
            val monto = binding.editTextCantidad.text.toString().toDoubleOrNull()
            if (monto != null && monto > 0) {
                saldo += monto
                guardarSaldo()
                actualizarSaldo()
                limpiarCampoCantidad()
                Toast.makeText(this, "Depósito exitoso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRetirar.setOnClickListener {
            val monto = binding.editTextCantidad.text.toString().toDoubleOrNull()
            if (monto != null && monto > 0) {
                if (saldo >= monto) {
                    saldo -= monto
                    guardarSaldo()
                    actualizarSaldo()
                    limpiarCampoCantidad()
                    Toast.makeText(this, "Retiro exitoso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }
    }


    private fun actualizarSaldo() {
        binding.tvSaldo.text = "Saldo: $${"%.2f".format(saldo)}"
    }

    private fun guardarSaldo() {
        val sharedPref = getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putFloat(pin, saldo.toFloat())
        editor.apply()
    }

    private fun limpiarCampoCantidad() {
        binding.editTextCantidad.text.clear()
    }

    private fun cerrarSesion() {
        val intent = Intent(this, IniciarSesionActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}