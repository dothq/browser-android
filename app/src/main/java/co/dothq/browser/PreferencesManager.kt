package co.dothq.browser

import android.content.Context
import android.util.Log

class PreferencesManager {
    fun get(id: String) {

    }

    fun set(id: String, value: Any) {

    }

    fun delete(id: String) {

    }

    fun init(context: Context) {
        var data: String = ""
        context.assets.open("profile.js").apply {
            data = this.readBytes().toString(Charsets.UTF_8);
        }.close()
        Log.d("sex", data);
    }
}