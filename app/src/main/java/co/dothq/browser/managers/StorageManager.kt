package co.dothq.browser.managers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import co.dothq.browser.util.defaultProfile
import java.lang.Exception
import java.lang.NumberFormatException

class StorageManager {

    fun get(context: Context, id: String, table: String, defaultValue: Any?): Any? {
        val preferences: SharedPreferences =
            context.getSharedPreferences(table, Context.MODE_PRIVATE);

        val typePreferences: SharedPreferences = context.getSharedPreferences("types-${table}", Context.MODE_PRIVATE)

        val type = typePreferences.getString(id, null);

        Log.d("sex", "${type} (${id})")
        if (type == "String") {
            return preferences.getString(id, null) ?: defaultValue;
        }

        if (type == "Int") {
            return preferences.getInt(id, 0) ?: defaultValue;
        }

        if (type == "Boolean") {
            Log.d("sex", preferences.getBoolean(id, false).toString())
            return preferences.getBoolean(id, false) ?: defaultValue;
        }

        return null ?: defaultValue;
    }

    fun set(context: Context, id: String, value: Any, table: String) {
        Log.d("sex", "Setting: '${id}' => ${value.toString()} IN ${table}")
        val preferences: SharedPreferences =
            context.getSharedPreferences(table, Context.MODE_PRIVATE);

        val typePreferences: SharedPreferences = context.getSharedPreferences("types-${table}", Context.MODE_PRIVATE)
        val type: String = "${value::class.simpleName}";

        val typesEditMode = typePreferences.edit();
        val preferencesEditMode = preferences.edit();

        Log.d("sex", "Set:: ${type} (${id})")
        if (value is String) {
            typesEditMode.putString(id, type)
            preferencesEditMode.putString(id, value.toString());
        }
        if (value is Int) {
            typesEditMode.putString(id, type)
            preferencesEditMode.putInt(id, value);
        }
        if (value is Boolean) {
            typesEditMode.putString(id, type)
            preferencesEditMode.putBoolean(id, value);
        }

        typesEditMode.commit()
        preferencesEditMode.commit()
    }

    fun delete(context: Context, id: String, table: String) {

    }
}