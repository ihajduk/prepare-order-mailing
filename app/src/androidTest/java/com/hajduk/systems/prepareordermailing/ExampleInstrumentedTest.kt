package com.hajduk.systems.prepareordermailing

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.WooCommerceClient
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        WooCommerceClient("http://10.0.2.2:8080", "ck_ac7d7c71127f8399ede3dbbf433744ef3266bb52", "cs_c0c0fd9fe19a81647add48ebee9c1522a361461e")
            .getOrderFuel("12",
                { println("success $it") },
                { println("failure $it") }
            )
    }
}
