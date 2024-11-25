package com.infinium.directzap.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hbb20.CountryCodePicker
import com.infinium.directzap.R
import com.infinium.directzap.util.WhatsAppUtil

class DirectChatActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_direct_chat)

        //show splash screen
        installSplashScreen()

        val ccp = findViewById<CountryCodePicker>(R.id.countryCodePicker)
        val editTextPhoneNumber = findViewById<EditText>(R.id.editTextPhoneNumber)
        val editTextMessage = findViewById<EditText>(R.id.editTextMessage)
        val radioGroupAppType = findViewById<RadioGroup>(R.id.radioGroupAppType)
        val buttonSend = findViewById<Button>(R.id.buttonSend)

        buttonSend.setOnClickListener {
            val countryCode = ccp.selectedCountryCodeWithPlus
            val phoneNumber = editTextPhoneNumber.text.toString()
            val message = editTextMessage.text.toString()
            val fullPhoneNumber = countryCode + phoneNumber

            val isBusinessApp = radioGroupAppType.checkedRadioButtonId == R.id.radioButtonWB


            if (phoneNumber.isNotEmpty() && message.isNotEmpty()) {
                WhatsAppUtil.sendWhatsAppMessage(this, fullPhoneNumber, message, isBusinessApp)
            } else {
                showToast(getString(R.string.please_input_all_fields))
            }
        }
    }
}