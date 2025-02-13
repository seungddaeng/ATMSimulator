package com.example.atmsimulator

class ATMUtils {

    fun validarFormatoPIN(pin: String): Boolean {
        return pin.length == 4 && pin.all { it.isDigit() }
    }
}