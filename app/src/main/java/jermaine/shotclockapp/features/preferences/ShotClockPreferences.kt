package jermaine.shotclockapp.features.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import jermaine.shotclockapp.utils.SHOTCLOCKAPP_PREFERENCES
import jermaine.shotclockapp.utils.THEME_TYPE
import jermaine.shotclockapp.utils.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ShotClockPreferences(val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = SHOTCLOCKAPP_PREFERENCES
        )
        val THEME_TYPE_KEY = intPreferencesKey(THEME_TYPE)
    }

    suspend fun saveTheme(themeType: ThemeType) {
        context.dataStore.edit { preferences ->
            preferences[THEME_TYPE_KEY] = themeType.ordinal
        }
    }

    val theme: Flow<ThemeType> =
        context.dataStore.data.map { preferences ->
            ThemeType.values()[preferences[THEME_TYPE_KEY] ?: ThemeType.Light.ordinal]
        }
}