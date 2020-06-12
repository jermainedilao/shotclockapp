package jermaine.shotclockapp.view.activity

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import jermaine.shotclockapp.R
import jermaine.shotclockapp.extension.getThemeType
import jermaine.shotclockapp.extension.startMainActivityClrTsk
import jermaine.shotclockapp.extension.storeThemeType


class SettingsActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SettingsActivity"
    }

    private var hasChangedTheme = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun onThemeClick(view: View) {
        val popupMenu = PopupMenu(this, view, Gravity.END)
        popupMenu.menuInflater.inflate(R.menu.popup_menu_themes, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            hasChangedTheme = true
            when (it.itemId) {
                R.id.action_light -> {
                    storeThemeType(MainActivity.THEME_LIGHT)
                }
                R.id.action_dark -> {
                    storeThemeType(MainActivity.THEME_DARK)
                }
            }

            Log.d(TAG, "onThemeClick: ${getThemeType()}")
            true
        }
        popupMenu.show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (hasChangedTheme) {
            startMainActivityClrTsk()
        } else {
            super.onBackPressed()
        }
    }
}