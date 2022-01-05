package co.dothq.browser.subactivities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.dothq.browser.R
import co.dothq.browser.managers.PreferencesManager
import co.dothq.browser.managers.StorageManager


class AddressBar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initStatusbar()
        setContentView(R.layout.activity_address_bar)
        val uri :String = intent.getStringExtra("currentURI").toString();

        val contextualIdentity = StorageManager().get(applicationContext, "contextualIdentity", "appValues", false);

        val contextualIdentityIcon = findViewById<ImageView>(R.id.contextIdentityIcon);

        contextualIdentityIcon.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.ic_unsecure_filled));

        if (contextualIdentity is Boolean) {
            if (contextualIdentity) contextualIdentityIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_secure_filled))
            if (!contextualIdentity) contextualIdentityIcon.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_unsecure_filled));
        }

        val editBox = findViewById<EditText>(R.id.urlEnterBox);
        editBox.setText(uri);

        editBox.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editBox.selectAll()

        editBox.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text = editBox.text.toString().trim();

                    val uriRegex = """^(?:\w+:)?\/\/([^\s.]+\.\S{2}|localhost[:?\d]*)\S*$""".toRegex()

                    if (!uriRegex.matches(text)) {
                        if (uriRegex.matches("http://${text}")) {
                            text = "http://${text}"
                            contextualIdentityIcon.setImageDrawable(
                                ContextCompat.getDrawable(this@AddressBar, R.drawable.ic_globe));
                        } else {
                            text = "https://duckduckgo.com/?q=%s".replace("%s", text)
                            contextualIdentityIcon.setImageDrawable(
                                ContextCompat.getDrawable(this@AddressBar, R.drawable.ic_search));
                        }
                    } else {
                        contextualIdentityIcon.setImageDrawable(
                            ContextCompat.getDrawable(this@AddressBar, R.drawable.ic_globe));
                    }
            }

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        editBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->

            var text = editBox.text.toString().trim();

            if (event.action == KeyEvent.ACTION_UP) {
                val uriRegex = """^(?:\w+:)?\/\/([^\s.]+\.\S{2}|localhost[:?\d]*)\S*$""".toRegex()

                if (!uriRegex.matches(text)) {
                    if (uriRegex.matches("http://${text}")) {
                        text = "http://${text}"
                    } else {
                        text = "https://duckduckgo.com/?q=%s".replace("%s", text)
                    }
                }
            }

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                if (text.trim() == "") {
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                    overridePendingTransition(0, 0)
                } else {

                    val data = Intent()

                    data.putExtra("targetURI", text);
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