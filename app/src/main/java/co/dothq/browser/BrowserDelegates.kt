package co.dothq.browser

import android.app.Activity
import android.content.Context
import android.widget.TextView
import org.mozilla.geckoview.AllowOrDeny
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoSession

class BrowserDelegates {
    public fun createNavigationDelegate(area: String, context: Context, applicationCtx: Context): GeckoSession.NavigationDelegate {
        return object : GeckoSession.NavigationDelegate {

            override fun onLoadRequest(session: GeckoSession, request: GeckoSession.NavigationDelegate.LoadRequest): GeckoResult<AllowOrDeny>? {
                    StorageManager().set(applicationCtx, "dot.current.uri", request.uri.toString(), "appValues")
                    val uri = request.uri;
                    var uriNoProtocol = uri

                    if (uri.startsWith("https://")) uriNoProtocol = uriNoProtocol.replace("https://", "");
                    if (uri.startsWith("http://")) uriNoProtocol = uriNoProtocol.replace("http://", "");

                    if (uriNoProtocol.startsWith("www.")) uriNoProtocol = uriNoProtocol.replace("www.", "");

                    if (area == "main") {
                        val activity: Activity = (context as Activity)
                        activity.findViewById<TextView>(R.id.addressBarUri).text = uriNoProtocol.toString()
                    }
                    return (GeckoResult.allow())
            }
        }
    }
}