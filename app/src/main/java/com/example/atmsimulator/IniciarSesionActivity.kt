package com.example.atmsimulator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atmsimulator.databinding.ActivityIniciarSesionBinding

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIniciarSesionBinding
    private val pinCorrecto = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonIniciarSesion.setOnClickListener {
            val usuario = binding.editTextCorreo.text.toString()
            val pin = binding.editTextContrasena.text.toString()

            if (usuario.isNotEmpty() && pin == pinCorrecto) {
                val intent = Intent(this, ATMActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o PIN incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
