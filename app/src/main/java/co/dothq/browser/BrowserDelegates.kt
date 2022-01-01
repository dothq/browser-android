package co.dothq.browser

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.dothq.browser.managers.StorageManager
import org.mozilla.geckoview.AllowOrDeny
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoSession

class BrowserDelegates {
     fun createProgressDelegate(area: String, context: Context, applicationCtx: Context): GeckoSession.ProgressDelegate {
        return object : GeckoSession.ProgressDelegate {

            override fun onPageStop(session: GeckoSession, success: Boolean) = Unit

            override fun onSecurityChange(session: GeckoSession, securityInfo: GeckoSession.ProgressDelegate.SecurityInformation)  {

                if (area == "main") {
                    val activity: Activity = (context as Activity)
                    val contextualIdIcon = activity.findViewById<ImageView>(R.id.contextIdentityIcon);

                    if (securityInfo.isSecure) contextualIdIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_secure_filled))
                    if (!securityInfo.isSecure) contextualIdIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_unsecure_filled))
                    Toast.makeText(context, securityInfo.securityMode.toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onPageStart(session: GeckoSession, url: String) {

            }


            override fun onProgressChange(session: GeckoSession, progress: Int) {

            }
        }
    }

    public fun createNavigationDelegate(area: String, context: Context, applicationCtx: Context): GeckoSession.NavigationDelegate {
        return object : GeckoSession.NavigationDelegate {

            override fun onLoadRequest(session: GeckoSession, request: GeckoSession.NavigationDelegate.LoadRequest): GeckoResult<AllowOrDeny>? {
                    StorageManager().set(applicationCtx, "dot.current.uri", request.uri.toString(), "appValues")
                    val uri = request.uri;
                    var uriNoProtocol = uri

                    if (uri.startsWith("https://")) uriNoProtocol = uriNoProtocol.replace("https://", "");
                    if (uri.startsWith("http://")) uriNoProtocol = uriNoProtocol.replace("http://", "");

                    if (uriNoProtocol.startsWith("www.")) uriNoProtocol = uriNoProtocol.replace("www.", "");

                    var uriSplitBySlash = uriNoProtocol.split("/");
                    var uriJustHostname = uriSplitBySlash[0];
                    var path = uriSplitBySlash.drop(1).joinToString("/");

                if (area == "main") {
                    val activity: Activity = (context as Activity)

                    activity.findViewById<TextView>(R.id.addressBarDomain).text = uriJustHostname.toString();

                    if (path != "") activity.findViewById<TextView>(R.id.addressBarPath).text = "/${path.toString()}";
                    if (path == "") activity.findViewById<TextView>(R.id.addressBarPath).text = path.toString()
                    }
                    return (GeckoResult.allow())
            }
        }
    }
}