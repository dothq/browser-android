package co.dothq.browser

import android.content.Context

fun pref(id: String, value: Any, context: Context) {
    PreferencesManager().set(context, id, value)
}

class PreferencesManager {
    fun get(context: Context, id: String) : Any? {
        val preference = StorageManager().get(context, id, "preferences", null);
        return preference;
    }

    fun set(context: Context, id: String, value: Any) {
        StorageManager().set(context, id, value, "preferences");
    }

    fun delete(context: Context, id: String) {
        StorageManager().delete(context, id, "preferences");
    }
}