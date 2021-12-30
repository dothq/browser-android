package co.dothq.browser

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import co.dothq.browser.util.defaultProfile
import android.app.Activity




class ApplicationManager {
    fun startUp(context: Context, thisContext: Context) {
        val appSetup = StorageManager().get(context, "setup", "appValues", false);
        if (appSetup == false) {
            Toast.makeText(context, "App Not Setup", Toast.LENGTH_SHORT).show()
            val welcomeIntent = Intent(thisContext, WelcomeActivity::class.java);
            welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            thisContext.startActivity(welcomeIntent);
            (thisContext as Activity).finish()
        } else {
            Toast.makeText(context, "App Setup!", Toast.LENGTH_SHORT).show()
        }
    }
}