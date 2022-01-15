package co.dothq.browser

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import co.dothq.browser.managers.ApplicationManager
import co.dothq.browser.managers.PreferencesManager
import co.dothq.browser.managers.StorageManager
import co.dothq.browser.subactivities.AddressBar
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import java.net.URL


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
        val runtime = GeckoRuntime.getDefault(this)
        val session = GeckoSession()

        session.open(runtime)
        view.setSession(session)

        session.navigationDelegate = BrowserDelegates().createNavigationDelegate("main", this, applicationContext);
        session.progressDelegate = BrowserDelegates().createProgressDelegate("main", this, applicationContext);

        var addressBarLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data

                val url = data?.getStringExtra("targetURI")?.toUri();

                val host = url?.host.toString();
                val path = url.toString().replace("${url?.scheme}://${url?.host}", "");

                findViewById<TextView>(R.id.addressBarDomain).text = host.toString();

                if (path != "/") findViewById<TextView>(R.id.addressBarPath).text = path
                if (path == "/") findViewById<TextView>(R.id.addressBarPath).text = ""

                session.loadUri(url.toString())
            }
        }


        val addressBar = findViewById<LinearLayout>(R.id.addressBarContainer);

        addressBar.setOnClickListener {
            val addressBarIntent = Intent(this, AddressBar::class.java);
            val contextualIdentityIcon = findViewById<ImageView>(R.id.contextIdentityIcon);

            addressBarIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            addressBarIntent.putExtra("currentURI", StorageManager().get(applicationContext, "currentUri", "appValues", "about:blank").toString())
            addressBarLauncher.launch(addressBarIntent)
            overridePendingTransition(0, 0);
        }

        val deeplinkHeader = findViewById<LinearLayout>(R.id.deeplinkAddressBar);
        val browserHeader = findViewById<LinearLayout>(R.id.normalAddressbar);

        try {
            val intent = intent
            val uri: Uri? = intent.data
            browserHeader.visibility = View.GONE;
            deeplinkHeader.visibility = View.VISIBLE;

            if (uri.toString() == "") {
                browserHeader.visibility = View.VISIBLE;
                deeplinkHeader.visibility = View.GONE;
            }
            val url = URL(uri?.getScheme(), uri?.getHost(), uri?.getPath())
            session.loadUri(url.toString())
            deepLinkActivitySetup()

        } catch (e: Exception) {
            browserHeader.visibility = View.VISIBLE;
            deeplinkHeader.visibility = View.GONE;
            session.loadUri("https://ddg.gg")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun initStatusbar() {
        if (resources.getString(R.string.mode) == "Day") {
            getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {

        }
    }

    fun deepLinkActivitySetup() {
        val deeplinkHeader = findViewById<LinearLayout>(R.id.deeplinkAddressBar);
        val browserHeader = findViewById<LinearLayout>(R.id.normalAddressbar);

        val deeplinkCloseButton = findViewById<LinearLayout>(R.id.closeButtonDeeplink);
        val deeplinkOpenInBrowserButton = findViewById<LinearLayout>(R.id.openInBrowserButtonDeeplink);

        deeplinkCloseButton.setOnClickListener {
            finish()
        }

        deeplinkOpenInBrowserButton.setOnClickListener {
            browserHeader.visibility = View.VISIBLE;
            deeplinkHeader.visibility = View.GONE;
        }
    }
}