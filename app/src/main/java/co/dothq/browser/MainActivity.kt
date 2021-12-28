package co.dothq.browser

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_main)
        val view = findViewById<GeckoView>(R.id.geckoview)
        val session = GeckoSession()
        val runtime = GeckoRuntime.create(this)

        session.open(runtime)
        view.setSession(session)
        session.loadUri("https://dothq.co")
    }
}