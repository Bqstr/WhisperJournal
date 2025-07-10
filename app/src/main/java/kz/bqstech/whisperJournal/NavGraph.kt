package kz.bqstech.whisperJournal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Composable
fun MainNavGraph(){





    val navController = rememberNavController()



    val navigateToHome ={
        navController.navigate(HomeScreen)
    }
    val navigateToJournal ={
        navController.navigate(JournalScreen)
    }
    val navigateToSettings ={
        navController.navigate(SettingsScreen)
    }



    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {}

    NavHost(navController = navController , startDestination = HomeScreen){
        composable<HomeScreen> {
            HomeScreen(navController, navigateToHome = navigateToHome,navigateToJournal =navigateToJournal , navigateToSettings =navigateToSettings )
        }
        composable<JournalScreen> {
            SearchBarJournalScreen()
        }
        composable<SettingsScreen> {
            HomeScreen(navController, navigateToHome = navigateToHome,navigateToJournal =navigateToJournal , navigateToSettings =navigateToSettings )
        }
    }





}


@Serializable
object HomeScreen

@Serializable
object JournalScreen

@Serializable
object SettingsScreen