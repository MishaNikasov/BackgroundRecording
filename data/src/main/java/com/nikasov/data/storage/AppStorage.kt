package com.nikasov.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nikasov.domain.storage.AppStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppStorageImpl(
    context: Context
) : AppStorage {

    companion object {
        private val LAST_SYNCED_VERSION = stringPreferencesKey("last_synced_version")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override val lastSyncedVersion: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[LAST_SYNCED_VERSION].orEmpty() }

}