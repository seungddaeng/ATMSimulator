package com.example.atmsimulator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atmsimulator.databinding.ActivityAtmBinding

class ATMActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtmBinding
    private var saldo = 10000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actualizarSaldo()

        binding.btnDepositar.setOnClickListener {
            val monto = binding.editTextCantidad.text.toString().toDoubleOrNull()
            if (monto != null && monto > 0) {
                saldo += monto
                actualizarSaldo()
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
                    actualizarSaldo()
                    Toast.makeText(this, "Retiro exitoso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun actualizarSaldo() {
        binding.tvSaldo.text = "Saldo: $${"%.2f".format(saldo)}"
    }
}