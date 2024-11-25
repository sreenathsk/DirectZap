package com.infinium.directzap.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.infinium.directzap.R

object WhatsAppUtil {

    /**
     * Sends a WhatsApp or WhatsApp Business message to the specified phone number.
     *
     * @param context The application context.
     * @param phoneNumber The full phone number in international format (e.g., "+1234567890").
     * @param message The message to send.
     * @param isBusinessApp True to send via WhatsApp Business, false for standard WhatsApp.
     */


    private const val WA: String = "com.whatsapp"
    private const val WB: String = "com.whatsapp.w4b"
    fun sendWhatsAppMessage(
        context: Context,
        phoneNumber: String,
        message: String,
        isBusinessApp: Boolean = false
    ) {
        try {
            val uri = Uri.parse(
                "https://api.whatsapp.com/send?phone=$phoneNumber&text=${
                    Uri.encode(message)
                }"
            )
            val sendIntent = Intent(Intent.ACTION_VIEW, uri)

            // Determine the package name based on whether sending to WhatsApp or WhatsApp Business
            val packageName = if (isBusinessApp) WA else WB
            sendIntent.setPackage(packageName)

            // Check if the chosen WhatsApp app is installed
            if (sendIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(sendIntent)
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.whatsapp_not_installed), Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.error, e.message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Validates if the given number is in the correct international format.
     *
     * @param phoneNumber The phone number to validate.
     * @return True if valid, false otherwise.
     */
    fun isValidInternationalNumber(phoneNumber: String): Boolean {
        val regex = Regex("^\\+[1-9]\\d{1,14}$")
        return regex.matches(phoneNumber)
    }
}