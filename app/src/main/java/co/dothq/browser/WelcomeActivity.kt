package co.dothq.browser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import co.dothq.browser.util.defaultProfile

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val startButton = findViewById<LinearLayout>(R.id.startButton)
        startButton.setOnClickListener {
            defaultProfile().applyDefaultPreferences(applicationContext);
            StorageManager().set(applicationContext, "setup", true, "appValues");
            val browserIntent = Intent(this, BrowserActivity::class.java);
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(browserIntent)
            this.finish()
        }
    }
}