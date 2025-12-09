package com.localgo.artelabspa.data.repository

import android.content.Context//recursos y servicios
import android.net.Uri// para referenciar contenido y archivos
import androidx.datastore.core.DataStore//interfaz principal de datos
import androidx.datastore.preferences.core.Preferences// tipos de datos(preferencias)
import androidx.datastore.preferences.core.edit// editar y guardar
import androidx.datastore.preferences.core.stringPreferencesKey//crea claves string para preferencias
import androidx.datastore.preferences.preferencesDataStore//delegate para crear y obtener dataStore<preferences>
import kotlinx.coroutines.flow.Flow//flujo asíncrono(reactivo)
import kotlinx.coroutines.flow.map//operador para transformar valores de un Flow

// Extensión para crear el DataStore de avatar
private val Context.avatarDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "avatar_preferences"
)

class AvatarRepository(private val context: Context) {

    companion object {
        // Key para almacenar el URI del avatar en DataStore
        private val AVATAR_URI_KEY = stringPreferencesKey("avatar_uri_key")
    }

    /**
     * Obtiene el URI del avatar como Flow reactivo.
     * Emite cada vez que cambia el avatar guardado.
     */
    fun getAvatarUri(): Flow<Uri?> {
        return context.avatarDataStore.data.map { preferences ->
            preferences[AVATAR_URI_KEY]?.let { uriString ->
                Uri.parse(uriString)
            }
        }
    }

    /**
     * Guarda el URI del avatar en DataStore.
     * El cambio se persiste inmediatamente.
     */
    suspend fun saveAvatarUri(uri: Uri?) {
        if (uri != null) {
            context.avatarDataStore.edit { preferences ->
                preferences[AVATAR_URI_KEY] = uri.toString()
            }
        } else {
            clearAvatarUri()
        }
    }

    /**
     * Elimina el URI del avatar de DataStore.
     */
    suspend fun clearAvatarUri() {
        context.avatarDataStore.edit { preferences ->
            preferences.remove(AVATAR_URI_KEY)
        }
    }
}