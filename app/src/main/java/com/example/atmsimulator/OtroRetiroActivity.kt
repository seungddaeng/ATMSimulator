package com.example.atmsimulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.atmsimulator.databinding.ActivityDepositoBinding
import com.example.atmsimulator.databinding.ActivityOtroRetiroBinding
import com.example.atmsimulator.databinding.ActivityRetiroBinding

class OtroRetiroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtroRetiroBinding
    private var saldo = 0.0
    private var pin = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtroRetiroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pin = intent.getStringExtra("PIN") ?: ""
        saldo = intent.getDoubleExtra("SALDO", 0.0)
        binding.tvSaldo.text = "Saldo: $${"%.2f".format(saldo)}"

        binding.btn1.setOnClickListener { agregarNumero("1") }
        binding.btn2.setOnClickListener { agregarNumero("2") }
        binding.btn3.setOnClickListener { agregarNumero("3") }
        binding.btn4.setOnClickListener { agregarNumero("4") }
        binding.btn5.setOnClickListener { agregarNumero("5") }
        binding.btn6.setOnClickListener { agregarNumero("6") }
        binding.btn7.setOnClickListener { agregarNumero("7") }
        binding.btn8.setOnClickListener { agregarNumero("8") }
        binding.btn9.setOnClickListener { agregarNumero("9") }
        binding.btn0.setOnClickListener { agregarNumero("0") }

        binding.btnBorrar.setOnClickListener { borrarUltimoNumero() }

        binding.botonRealizarRetiro.setOnClickListener { realizarDeposito() }

        binding.botonMostrarPin.setOnClickListener {
            val display = binding.tvSaldo
            if (display.transformationMethod is PasswordTransformationMethod) {
                display.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                display.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            display.text = display.text.toString()
        }

    }

    private fun realizarDeposito() {
           val monto = binding.tvRetiroDisplay.text.toString().toDoubleOrNull()
          if (monto != null && monto > 0) {
              if (monto.toInt() % 10 == 0) {
                  if (monto <= saldo) {
                      saldo -= monto
                      guardarSaldo()
                      actualizarSaldo()
                      limpiarCampoCantidad()
                      Toast.makeText(this, "Depósito exitoso", Toast.LENGTH_SHORT).show()
                      cerrarSesion()
                  } else {
                      Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
                  }
              } else {
                    Toast.makeText(this, "El monto debe ser múltiplo de 10", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
            }
    }

    private fun agregarNumero(numero: String) {
        val currentText = binding.tvRetiroDisplay.text.toString()
        binding.tvRetiroDisplay.text = currentText + numero

    }

    private fun borrarUltimoNumero() {
        val currentText = binding.tvRetiroDisplay.text.toString()
        if (currentText.isNotEmpty()) {
            binding.tvRetiroDisplay.text = currentText.dropLast(1)
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
        binding.tvRetiroDisplay.text = ""
    }

    private fun cerrarSesion() {
        val intent = Intent(this, IniciarSesionActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}