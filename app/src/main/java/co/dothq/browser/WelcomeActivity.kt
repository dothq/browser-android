package co.dothq.browser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import co.dothq.browser.managers.StorageManager
import co.dothq.browser.util.defaultProfile
import java.util.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val year = Calendar.getInstance().get(Calendar.YEAR);

        val copyrightInfo = findViewById<TextView>(R.id.copyrightText)

        copyrightInfo.text = "© ${year.toString()} Dot HQ"

        val startButton = findViewById<LinearLayout>(R.id.startButton)
        startButton.setOnClickListener {
            defaultProfile().applyDefaultPreferences(applicationContext);
            StorageManager().set(applicationContext, "setup", true, "appValues");
            val browserIntent = Intent(this, BrowserActivity::class.java);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(browserIntent)
            overridePendingTransition(0, 0);
            this.finish()
        }
    }
}