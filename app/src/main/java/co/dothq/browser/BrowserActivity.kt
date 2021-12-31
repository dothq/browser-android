package co.dothq.browser

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.widget.Toast
import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;
import co.dothq.browser.PreferencesManager;
import co.dothq.browser.BrowserDelegates



class BrowserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        this.initStatusbar();

        ApplicationManager().init(applicationContext, this);
        setContentView(R.layout.activity_main);

        val accentColour: Any? = PreferencesManager().get(applicationContext, "dot.ui.accent_colour");
        Toast.makeText(applicationContext, accentColour.toString(), Toast.LENGTH_SHORT).show()

        val appSetup = StorageManager().get(applicationContext, "setup", "appValues", false);

        if (appSetup == false) return;
        val view = findViewById<GeckoView>(R.id.geckoview)
        val session = GeckoSession()
        val runtime = GeckoRuntime.create(this)

        session.open(runtime)
        view.setSession(session)
        session.navigationDelegate = BrowserDelegates().createNavigationDelegate("main", this, applicationContext);
        session.loadUri("https://ddg.gg")
    }

    fun initStatusbar() {
        if (resources.getString(R.string.mode) == "Day") {
            getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {

        }
    }
}