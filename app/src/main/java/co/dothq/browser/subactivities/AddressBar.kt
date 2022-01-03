package co.dothq.browser.subactivities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.dothq.browser.R
import co.dothq.browser.managers.StorageManager


class AddressBar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initStatusbar()
        setContentView(R.layout.activity_address_bar)
        val uri :String = intent.getStringExtra("currentURI").toString();

        val editBox = findViewById<EditText>(R.id.urlEnterBox);
        editBox.setText(uri);

        editBox.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editBox.selectAll()

        editBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                var text = editBox.text.toString();
                if (text.trim() == "") {
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                    overridePendingTransition(0, 0)
                } else {

                    val data = Intent()
                    data.putExtra("targetURI", text.trim());
                    // more handling here
                    setResult(Activity.RESULT_OK, data);
                    finish()
                    overridePendingTransition(0, 0)
                }
                return@OnKeyListener true
            }
            false
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }

    fun initStatusbar() {
        if (resources.getString(R.string.mode) == "Day") {
            getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {

        }
    }
}