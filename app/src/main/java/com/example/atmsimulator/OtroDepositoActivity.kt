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
import com.example.atmsimulator.databinding.ActivityOtroDepositoBinding

class OtroDepositoActivity : AppCompatActivity() {
    private var saldo = 0.0
    private var pin = ""
    private var saldoVisible = false

    private lateinit var binding: ActivityOtroDepositoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtroDepositoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pin = intent.getStringExtra("PIN") ?: ""
        saldo = intent.getDoubleExtra("SALDO", 0.0)
        actualizarSaldo()



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

        binding.botonRealizarDeposito.setOnClickListener { realizarDeposito() }

        binding.botonMostrarPin.setOnClickListener {
            saldoVisible = !saldoVisible
            actualizarSaldo()
        }
    }


//    private fun realizarDeposito() {
//        val monto = binding.tvDepositoDisplay.text.toString().toDoubleOrNull()
//        if (monto != null && monto > 0) {
//            // Verificar si el monto es múltiplo de 10
//            if (monto.toInt() % 10 == 0) {
//                // Verificar si hay saldo suficiente
//                if (monto <= saldo) {
//                    saldo -= monto
//                    guardarSaldo()
//                    actualizarSaldo()
//                    limpiarCampoCantidad()
//                    Toast.makeText(this, "Depósito exitoso", Toast.LENGTH_SHORT).show()
//                    cerrarSesion()
//                } else {
//                    Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "El monto debe ser múltiplo de 10", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
//        }
//    }
    private fun realizarDeposito() {
        val monto = binding.tvDepositoDisplay.text.toString().toDoubleOrNull()
        if (monto != null && monto > 0) {
            val saldoAnterior = saldo
            saldo += monto
            guardarSaldo()

            val intent = Intent(this, ComprobanteActivity::class.java)
            intent.putExtra("PIN", pin)
            intent.putExtra("SALDO_ANTERIOR", saldoAnterior)
            intent.putExtra("MONTO", monto)
            intent.putExtra("SALDO_NUEVO", saldo)
            intent.putExtra("TIPO", "Depósito")
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
        }
    }



    private fun agregarNumero(numero: String) {
            val currentText = binding.tvDepositoDisplay.text.toString()
            binding.tvDepositoDisplay.text = currentText + numero
        }

        private fun borrarUltimoNumero() {
            val currentText = binding.tvDepositoDisplay.text.toString()
            if (currentText.isNotEmpty()) {
                binding.tvDepositoDisplay.text = currentText.dropLast(1)
            }
        }
    private fun actualizarSaldo() {
        if (saldoVisible) {
            binding.tvSaldo.text = "Saldo: $${"%.2f".format(saldo)}"
            binding.botonMostrarPin.setImageResource(R.drawable.ojo)
        } else {
            binding.tvSaldo.text = "Saldo: ****"
            binding.botonMostrarPin.setImageResource(R.drawable.ojo_cerrado)
        }
    }

    private fun guardarSaldo() {
        val sharedPref = getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putFloat(pin, saldo.toFloat())
        editor.apply()
    }

    private fun limpiarCampoCantidad() {
        binding.tvDepositoDisplay.text = ""
    }
    private fun cerrarSesion() {
        val intent = Intent(this, IniciarSesionActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    }
