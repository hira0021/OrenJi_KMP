package com.example.orenji.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.orenji.presentation.map.FamilyMapState
import com.example.orenji.presentation.map.FamilyMapViewModel
import com.example.orenji.ui.theme.OrenJiTheme
import org.koin.compose.viewmodel.koinViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun FamilyMapScreen(
    viewModel: FamilyMapViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.onPermissionGranted()
        else viewModel.onPermissionDenied()
    }

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) viewModel.onPermissionGranted()
        else permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    FamilyMapContent(
        state = state,
        onRequestPermission = { viewModel.onRequestPermission() },
    )
}

@Composable
fun FamilyMapContent(
    state: FamilyMapState,
    onRequestPermission: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.permissionGranted -> {
                OsmMapView(
                    latitude = state.userLatitude,
                    longitude = state.userLongitude,
                )
            }
            state.isRequestingPermission -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            else -> {
                PermissionDeniedContent(
                    onRequestPermission = onRequestPermission,
                )
            }
        }
    }
}

@Composable
fun OsmMapView(
    latitude: Double = -6.2088,
    longitude: Double = 106.8456,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val mapView = remember { createOsmMapView(context, latitude, longitude) }

    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize(),
        update = { _ -> }
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE  -> mapView.onPause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDetach()
        }
    }
}

private fun createOsmMapView(
    context: Context,
    latitude: Double,
    longitude: Double,
): MapView {
    // set user agent — required by OSM tile policy
    Configuration.getInstance().userAgentValue = context.packageName

    return MapView(context).apply {
        setTileSource(TileSourceFactory.MAPNIK)
        setMultiTouchControls(true)
        isTilesScaledToDpi = true

        // set initial position
        controller.setZoom(15.0)
        controller.setCenter(GeoPoint(latitude, longitude))

        // show my location dot
        val locationOverlay = MyLocationNewOverlay(
            GpsMyLocationProvider(context),
            this,
        )
        locationOverlay.enableMyLocation()
        locationOverlay.enableFollowLocation()
        overlays.add(locationOverlay)
    }
}

// ── Permission Denied UI ──────────────────────────────────────────────────────

@Composable
fun PermissionDeniedContent(
    onRequestPermission: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(24.dp),
        ) {
            Text(
                text = "📍",
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = "Location Permission Required",
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "OrenJi needs your location to show family members on the map.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Button(
                onClick = onRequestPermission,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Text("Grant Permission")
            }
        }
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Map - Permission Denied")
@Composable
private fun MapPermissionDeniedPreview() {
    OrenJiTheme {
        FamilyMapContent(
            state = FamilyMapState(
                permissionGranted = false,
                isRequestingPermission = false,
            ),
            onRequestPermission = {},
        )
    }
}

@Preview(showBackground = true, name = "Map - Requesting")
@Composable
private fun MapRequestingPreview() {
    OrenJiTheme {
        FamilyMapContent(
            state = FamilyMapState(
                permissionGranted = false,
                isRequestingPermission = true,
            ),
            onRequestPermission = {},
        )
    }
}