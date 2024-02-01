package com.nikasov.backgroundrecording

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.nikasov.backgroundrecording.screen.root.RootScreen
import com.nikasov.common.manager.permissionManager.PermissionCallback
import com.nikasov.common.manager.permissionManager.PermissionManager
import com.nikasov.common.manager.permissionManager.PermissionManagerImpl
import com.nikasov.theme.BackgroundRecordingTheme
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "App log"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionManager: PermissionManager by lazy { PermissionManagerImpl() }

    private val readStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Log.i(TAG, "Read storage permission granted")
        } else {
            Log.i(TAG, "Read storage permission not granted")
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestStoragePermission()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackgroundRecordingTheme {
                RootScreen()
            }
        }
    }

}
