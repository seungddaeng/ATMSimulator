package com.example.atmsimulator

import android.content.Context
import android.provider.ContactsContract
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ATMUtilsTest {

    @Test
    fun testGuardarYObtenerSaldo() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val sharedPref = context.getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)

        val editor = sharedPref.edit()
        editor.putFloat("1234", 5000.0f)
        editor.apply()

        val saldo = sharedPref.getFloat("1234", 0.0f)
        assertEquals(5000.0f, saldo)
    }


    @get:Rule
    val activityRule = ActivityScenarioRule(IniciarSesionActivity::class.java)

    @Test
    fun testActivitySeInicia() {
        activityRule.scenario.onActivity { activity ->
            assert(activity != null)
        }
    }

}