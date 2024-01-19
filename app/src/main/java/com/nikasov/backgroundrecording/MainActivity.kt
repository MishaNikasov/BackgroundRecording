package com.nikasov.backgroundrecording

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.nikasov.backgroundrecording.screen.HomeScreen
import com.nikasov.backgroundrecording.ui.theme.BackgroundRecordingTheme
import com.nikasov.common.permissionManager.PermissionCallback
import com.nikasov.common.permissionManager.PermissionManager
import com.nikasov.common.permissionManager.PermissionManagerImpl
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "App log"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionManager: PermissionManager by lazy { PermissionManagerImpl() }

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
    }

    private fun requestAudioRecordingPermission() {
        permissionManager.requestPermission(this, Manifest.permission.RECORD_AUDIO) { permissionCallback ->
            when (permissionCallback) {
                PermissionCallback.Granted -> return@requestPermission
                PermissionCallback.NotGranted -> audioRecordingPermission.launch(Manifest.permission.RECORD_AUDIO)
                PermissionCallback.Rationale -> audioRecordingPermission.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackgroundRecordingTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .statusBarsPadding()
                ) {
                    HomeScreen()
                }
            }
        }
    }

}
