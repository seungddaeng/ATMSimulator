package com.example.atmsimulator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ATMUtilsTest {



    @Test
    fun validarFormatoPIN_correcto() {
        val ATMUtils = ATMUtils()
        assertTrue(ATMUtils.validarFormatoPIN("1234"))
    }

    @Test
    fun validarFormatoPIN_incorrecto() {
        val ATMUtils = ATMUtils()
        assertFalse(ATMUtils.validarFormatoPIN("12"))
        assertFalse(ATMUtils.validarFormatoPIN("abcd"))
        assertFalse(ATMUtils.validarFormatoPIN("12345"))
    }



}
