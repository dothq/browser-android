package co.dothq.browser

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;
import co.dothq.browser.managers.ApplicationManager
import co.dothq.browser.managers.PreferencesManager
import co.dothq.browser.managers.StorageManager
import co.dothq.browser.subactivities.AddressBar


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
        session.progressDelegate = BrowserDelegates().createProgressDelegate("main", this, applicationContext);

        var addressBarLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data

                session.loadUri(data?.getStringExtra("targetURI").toString())
            }
        }


        val addressBar = findViewById<LinearLayout>(R.id.addressBarContainer);

        addressBar.setOnClickListener {
            val addressBarIntent = Intent(this, AddressBar::class.java);

            addressBarIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            addressBarIntent.putExtra("currentURI", StorageManager().get(applicationContext, "currentUri", "appValues", "about:blank").toString())
            addressBarLauncher.launch(addressBarIntent)
            overridePendingTransition(0, 0);
        }

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