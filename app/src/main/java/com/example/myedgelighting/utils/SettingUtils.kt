package com.example.myedgelighting.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object SettingUtils {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun goToPrivacyPolicy(context: Context) {
        val uri = Uri.parse("")


        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "Unable to open this link",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    fun shareApp(context: Context) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "EDGE LIGHTING APP")
        var shareMessage = "\nLet me recommend you an amazing application\n\n"
        shareMessage = """
            ${shareMessage}https://play.google.com/store/apps/details?id=${context.packageName}
            """.trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        context.startActivity(Intent.createChooser(shareIntent, "choose one"))
    }

    fun rateApp(context: Context) {
        val intent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + context.packageName)
            )

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Something went wrong. Try again", Toast.LENGTH_SHORT).show()
        }
    }

}