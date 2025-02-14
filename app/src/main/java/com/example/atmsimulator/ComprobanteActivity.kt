package com.example.atmsimulator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.atmsimulator.databinding.ActivityComprobanteBinding

class ComprobanteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComprobanteBinding
    private var saldoAnterior = 0.0
    private var montoTransaccion = 0.0
    private var saldoNuevo = 0.0
    private var pin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComprobanteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pin = intent.getStringExtra("PIN") ?: ""
        saldoAnterior = intent.getDoubleExtra("SALDO_ANTERIOR", 0.0)
        montoTransaccion = intent.getDoubleExtra("MONTO", 0.0)
        saldoNuevo = intent.getDoubleExtra("SALDO_NUEVO", 0.0)
        val tipoTransaccion = intent.getStringExtra("TIPO") ?: "Transacción"

        // Ahora los valores se pasan correctamente
        binding.tvTipoTransaccion.text = getString(R.string.comprobante_titulo, tipoTransaccion)
        binding.tvSaldoAnterior.text = getString(R.string.saldo_anterior, saldoAnterior)
        binding.tvMontoTransaccion.text = getString(R.string.monto_transaccion, montoTransaccion)
        binding.tvSaldoNuevo.text = getString(R.string.saldo_nuevo, saldoNuevo)

        binding.btnRealizarOtraTransaccion.text = getString(R.string.btn_realizar_otra_transaccion)
        binding.btnRealizarOtraTransaccion.setOnClickListener {
            volverAIniciarSesion()
        }
    }

    private fun volverAIniciarSesion() {
        val intent = Intent(this, IniciarSesionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
