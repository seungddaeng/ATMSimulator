package com.example.atmsimulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atmsimulator.databinding.ActivityIniciarSesionBinding

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIniciarSesionBinding
    val ATMUtils = ATMUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        inicializarCuentas()

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


        //mostrar/ocultar el PIN
        binding.botonMostrarPin.setOnClickListener {
            val display = binding.tvDisplay
            if (display.transformationMethod is PasswordTransformationMethod) {
                display.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                display.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            display.text = display.text.toString()
        }

        binding.botonIniciarSesion.setOnClickListener {
            val pin = binding.tvDisplay.text.toString()

            if (!ATMUtils.validarFormatoPIN(pin)) {
                Toast.makeText(this, "PIN inválido. Debe tener 4 dígitos numéricos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val saldo = obtenerSaldo(pin)
            if (saldo != null) {
                val intent = Intent(this, ATMActivity::class.java)
                intent.putExtra("PIN", pin)
                intent.putExtra("SALDO", saldo.toDouble())
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "PIN incorrecto.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun agregarNumero(numero: String) {
        val currentText = binding.tvDisplay.text.toString()
        // Limitar a 4 dígitos
        if (currentText.length < 4) {
            binding.tvDisplay.text = currentText + numero
        }
    }

    private fun borrarUltimoNumero() {
        val currentText = binding.tvDisplay.text.toString()
        if (currentText.isNotEmpty()) {
            binding.tvDisplay.text = currentText.dropLast(1)
        }
    }

    private fun inicializarCuentas() {
        val sharedPref = getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        if (!sharedPref.contains("1234")) {
            editor.putFloat("1234", 10000.0f)
        }
        if (!sharedPref.contains("5678")) {
            editor.putFloat("5678", 5000.0f)
        }

        editor.apply()
    }

    private fun obtenerSaldo(pin: String): Float? {
        val sharedPref = getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)
        return if (sharedPref.contains(pin)) {
            sharedPref.getFloat(pin, 0.0f)
        } else {
            null
        }
    }
}