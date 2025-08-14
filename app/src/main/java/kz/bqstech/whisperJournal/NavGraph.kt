package kz.bqstech.whisperJournal

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Composable
fun MainNavGraph() {


    val navController = rememberNavController()


    val navigateToHome = {
        navController.navigate(HomeScreen)
    }
    val navigateToJournal = {
        navController.navigate(JournalScreen)
    }
    val navigateToSettings = {
        navController.navigate(SettingsScreen)
    }


    val bottomBarScreenList =listOf(HomeScreen.javaClass.name,JournalScreen.javaClass.name,SettingsScreen.javaClass.name)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {}


    Scaffold(Modifier.fillMaxSize(),
        bottomBar = {
            val currentRoute =navController.currentDestination?.route ?: ""
            Log.d("asdfasdfasdfasdfasdf",currentRoute.toString())

            if(currentRoute in bottomBarScreenList) {
                MainBottomBar(
                    navController,
                    currentScreen =currentRoute,
                    navigateToHome = navigateToHome,
                    navigateToJournal = navigateToJournal,
                    navigateToSettings = navigateToSettings
                )
            }
},) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = HomeScreen,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<HomeScreen> {
                HomeScreen(
                    navController,
                    navigateToHome = navigateToHome,
                    navigateToJournal = navigateToJournal,
                    navigateToSettings = navigateToSettings
                )
            }
            composable<JournalScreen> {
                Log.d("asdfasdfasdfasdfasdf","journal")

                SearchBarJournalScreen()
            }
            composable<SettingsScreen> {
                HomeScreen(
                    navController,
                    navigateToHome = navigateToHome,
                    navigateToJournal = navigateToJournal,
                    navigateToSettings = navigateToSettings
                )
            }
        }

    }
    }


@Serializable
object HomeScreen

@Serializable
object JournalScreen

@Serializable
object SettingsScreen