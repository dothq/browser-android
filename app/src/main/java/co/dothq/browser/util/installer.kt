package co.dothq.browser.util

import android.content.Context

class installer {
    fun getInstallLocation(context: Context): String? {
        val appStoreIds = arrayOf(
            "com.android.vending"
        )

        val installerPkg = context
            .packageManager
            .getInstallerPackageName(context.packageName);

        return installerPkg
    }
}