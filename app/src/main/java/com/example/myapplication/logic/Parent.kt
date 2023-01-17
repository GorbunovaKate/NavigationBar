package com.example.myapplication.logic

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.myapplication.destinations.Destination
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.NavGraphs
import com.example.myapplication.appCurrentDestinationAsState
import com.example.myapplication.startAppDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Parent() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }

    ) {
        DestinationsNavHost(
          navController = navController,
         navGraph = NavGraphs.root
        )
    }
}

@SuppressLint("ResourceType")
@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    //if you are using material 2 then you  should use  bottom navigation bar
    NavigationBar(
        containerColor = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)//Высота бар
            .selectableGroup(),
    ) {
        BottomBarDestination.values().forEach { destination ->
            //similarly with material 2  use bottom nav item
            NavigationBarItem(

                selected  = currentDestination == destination.direction,
                onClick = {
                     navController.navigate(destination.direction) {
                         launchSingleTop = true
                         val navigationRoutes = BottomBarDestination.values()

                         val firstBottomBarDestination = navController.backQueue
                             .firstOrNull {navBackStackEntry -> checkForDestinations(navigationRoutes, navBackStackEntry) }
                             ?.destination
                         // remove all navigation items from the stack
                         // so only the currently selected screen remains in the stack
                         if (firstBottomBarDestination != null) {
                             popUpTo(firstBottomBarDestination.id) {
                                 inclusive = true
                                 saveState = true
                             }
                         }
                         // Avoid multiple copies of the same destination when
                         // reselecting the same item
                         launchSingleTop = false
                         // Restore state when reselecting a previously selected item
                         restoreState = false
                     }
                 },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = stringResource(destination.label),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Green, // <-- This doesn't work.
                    unselectedIconColor = Color.Red, // <-- This doesn't work.
                    indicatorColor = Color.Yellow, // <-- This works.
                    selectedTextColor = Color.Green
                ),
                label = { Text(stringResource(destination.label)) },
                alwaysShowLabel = false,

            )
        }
    }
}

fun checkForDestinations(
    navigationRoutes: Array<BottomBarDestination>,
    navBackStackEntry: NavBackStackEntry
): Boolean {
    navigationRoutes.forEach {
        if (it.direction.route == navBackStackEntry.destination.route){
            return  true
        }

    }
    return false
}
