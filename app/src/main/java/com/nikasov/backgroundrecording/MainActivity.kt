package com.nikasov.backgroundrecording

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikasov.backgroundrecording.screen.AudioViewModel
import com.nikasov.backgroundrecording.screen.HomeScreen
import com.nikasov.backgroundrecording.screen.RecordItem
import com.nikasov.backgroundrecording.ui.theme.BackgroundRecordingTheme
import com.nikasov.common.permissionManager.PermissionCallback
import com.nikasov.common.permissionManager.PermissionManager
import com.nikasov.common.permissionManager.PermissionManagerImpl
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "App log"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionManager: PermissionManager by lazy { PermissionManagerImpl() }

    private val viewModel: AudioViewModel by viewModels()

    private val readStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Log.i(TAG, "Read storage permission granted")
        } else {
            Log.i(TAG, "Read storage permission not granted")
        }
    }

    private val audioRecordingPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Log.i(TAG, "Recording permission granted")
        } else {
            Log.i(TAG, "Recording permission not granted")
        }
    }

    override fun onStart() {
        super.onStart()
        requestAudioRecordingPermission()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestStoragePermission()
        }
        viewModel.updateRecordings()
    }

    private fun requestAudioRecordingPermission() {
        val permission = Manifest.permission.RECORD_AUDIO
        permissionManager.requestPermission(this, permission) { permissionCallback ->
            when (permissionCallback) {
                PermissionCallback.Granted -> return@requestPermission
                PermissionCallback.NotGranted -> audioRecordingPermission.launch(permission)
                PermissionCallback.Rationale -> audioRecordingPermission.launch(permission)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestStoragePermission() {
        val permission = Manifest.permission.READ_MEDIA_AUDIO
        permissionManager.requestPermission(this, permission) { permissionCallback ->
            when (permissionCallback) {
                PermissionCallback.Granted -> return@requestPermission
                PermissionCallback.NotGranted -> readStoragePermission.launch(permission)
                PermissionCallback.Rationale -> readStoragePermission.launch(permission)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val medias by viewModel.medias.collectAsState(initial = emptyList())
            BackgroundRecordingTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .statusBarsPadding()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        HomeScreen()
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(medias) { media ->
                                val state = rememberDismissState(
                                    confirmValueChange = {
                                        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                            viewModel.remove(media)
                                            true
                                        } else false
                                    }
                                )
                                SwipeToDismiss(state = state, background = { }, dismissContent = {
                                    RecordItem(
                                        item = media,
                                        onClick = { viewModel.play(it) }
                                    )
                                })
                            }
                        }
                    }
                }
            }
        }
    }

}
