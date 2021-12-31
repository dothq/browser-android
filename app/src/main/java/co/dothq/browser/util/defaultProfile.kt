package co.dothq.browser.util

import android.content.Context
import co.dothq.browser.managers.pref

class defaultProfile {
    fun applyDefaultPreferences(context: Context) {
        pref("dot.ui.accent_colour", "blue", context) // Default accent colour is blue
        pref("dot.ui.locale", "", context) // If empty, use OS language
        pref("dot.ui.statusbar.disabled", false, context)
        pref("dot.ui.statusbar.type", "floating", context)
        pref("dot.ui.roundness", 8, context)

// dot.window
        pref("dot.window.nativecontrols.enabled", true, context)

// dot.tabs
        pref("dot.tabs.scroll_amount", 250, context)

// dot.keybinds
        pref("dot.keybinds.new-tab", "CmdOrCtrl+T", context)

// dot.newtab
        pref("dot.newtab.enabled", true, context)
        pref("dot.newtab.urls", "about:home", context) // accepts a single URL or urls split by |
    }
}