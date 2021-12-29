package co.dothq.browser

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

fun pref {
    PreferencesManager().set()
}

class PreferencesManager {
    fun get(context: Context, id: String, defaultValue: Any?): Any? {
        val preferences: SharedPreferences =
            context.getSharedPreferences("preferences", Context.MODE_PRIVATE);

        return preferences.getString(id, null) ?: defaultValue;
    }

    fun set(context: Context, id: String, value: Any) {

    }

    fun delete(id: String) {

    }

    fun init(context: Context) {
        var data: String = ""
        context.assets.open("profile.kt").apply {
            data = this.readBytes().toString(Charsets.UTF_8);
        }.close()

        val lines = data
            .split("\n")

        for(ln in lines) {
            if (ln.startsWith("/")) continue;

            var key: String = "";
            var value: String = "";

            val items = ln
                .replace(Regex("pref\\("), "")
                .replace(Regex("\\);?"), "")
                .split("/")[0]
                .find(Regex("\"*.\""),)

            Log.d("sex", items);
        }

        val preferences: SharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences.edit();
        editor.putString("yes", "no")

    }
}