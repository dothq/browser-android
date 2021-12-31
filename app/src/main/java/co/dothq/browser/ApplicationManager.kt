package co.dothq.browser

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import co.dothq.browser.util.defaultProfile
import android.app.Activity




class ApplicationManager {
    fun init(context: Context, thisContext: Context) {
        ThemeManager().init(context);

        val appSetup = StorageManager().get(context, "setup", "appValues", false);

        if (appSetup == false) {
            val welcomeIntent = Intent(thisContext, WelcomeActivity::class.java);

            welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            thisContext.startActivity(welcomeIntent);

            return (thisContext as Activity).finish();
        }
    }
}