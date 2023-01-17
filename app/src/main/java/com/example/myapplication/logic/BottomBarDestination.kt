package com.example.myapplication.logic

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapplication.R
import com.example.myapplication.destinations.HomeDestination
import com.example.myapplication.destinations.SettingsDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int

) {
    Home(HomeDestination, Icons.Default.Home, R.string.home),
    Settings(SettingsDestination, Icons.Default.Settings, R.string.settings),
}