package co.dothq.browser.managers

import android.content.Context
import android.util.Log
import co.dothq.browser.managers.PreferencesManager;


class ThemeManager {
    fun load(id: String?) {
        Log.i("ThemeManager", "Loading theme with ID ${id}.");
    }

    fun init(context: Context) {
        var currentThemeId = PreferencesManager().get(context, "dot.ui.theme");

        this.load(
            currentThemeId.toString()
        );
    }
}