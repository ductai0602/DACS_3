package com.example.dacs3_new.Activity.Login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dacs3_new.Activity.IntroScreen
import com.example.dacs3_new.Activity.MainScreen
import com.example.dacs3_new.ui.theme.DACS3_NEWTheme
import com.google.firebase.FirebaseApp

class MainLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            DACS3_NEWTheme {
                NavigationView()
            }
        }
    }
}

@Composable
fun NavigationView() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome" ){
        composable("welcome"){ IntroScreen(navController) }
        composable("login"){ LoginScreen(navController)}
        composable("signup"){ SignupScreen(navController)}
        composable("new"){ MainScreen(navController) }
    }

}