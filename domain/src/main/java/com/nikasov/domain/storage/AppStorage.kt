package com.nikasov.domain.storage

import kotlinx.coroutines.flow.Flow

interface AppStorage {
    val lastSyncedVersion: Flow<String>
}