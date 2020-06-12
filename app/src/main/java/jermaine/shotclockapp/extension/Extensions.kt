package jermaine.shotclockapp.extension

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import jermaine.shotclockapp.view.activity.MainActivity
import jermaine.shotclockapp.view.activity.SettingsActivity
import jermaine.shotclockapp.view.listener.VisibilityAnimatorListener

fun View.animateHide(duration: Long) {
    animate().alpha(0f)
            .setDuration(duration)
            .setListener(VisibilityAnimatorListener(this, View.GONE))
}

fun View.animateShow(duration: Long) {
    visibility = View.VISIBLE
    animate().alpha(1f)
            .setDuration(duration)
            .setListener(VisibilityAnimatorListener(this, View.VISIBLE))
}

fun Context.startSettingsActivity() = startActivity(Intent(this, SettingsActivity::class.java))

fun Context.startMainActivityClrTsk() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

fun Context.storeThemeType(themeType: String) = getMySharedPreferences().storeThemeType { putString("pref:theme_type", themeType) }

inline fun SharedPreferences.storeThemeType(putThemeType: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    edit().putThemeType().apply()
}

fun Context.getThemeType(): String = getMySharedPreferences().getString("pref:theme_type", MainActivity.THEME_LIGHT) ?: MainActivity.THEME_LIGHT

fun Context.getMySharedPreferences(): SharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
