package com.example.dacs3_new.Activity.Login

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dacs3_new.Activity.Login.Components.CButton
import com.example.dacs3_new.Activity.Login.Components.CTextField
import com.example.dacs3_new.Activity.Login.Components.DontHaveAcc
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.dacs3_new.Activity.Login.Components.ForgotPassword
import com.example.dacs3_new.Activity.Login.Components.GoogleStyleButton
import com.example.dacs3_new.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
//    val activity = context as Activity
    val auth = FirebaseAuth.getInstance()

    var isLoading by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showResetDialog by remember { mutableStateOf(false) }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("782192515330-18qnj9iaurtolad4ffma69dq5c43iia9.apps.googleusercontent.com")
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { firebaseTask ->
                        if (firebaseTask.isSuccessful) {
                            navController.navigate("new")
                        } else {
                            errorMessage = "Đăng nhập Google thất bại."
                        }
                    }
            } catch (e: Exception) {
                errorMessage = "Đăng nhập Google thất bại: ${e.localizedMessage}"
            }
        }
    }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {


        Box(modifier =  Modifier.fillMaxSize()){
            /// Background Image
            Image(
                painter = painterResource(R.drawable.background_intro),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            /// Content

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {

                // Logo
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 36.dp)
                        .height(150.dp)
                        .align(Alignment.Start)
                        .offset(x = (-20).dp)
                )

                Text(text = "Sign In",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight(500),
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                Text("Sign In now to access your exercises and saved music.",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xB2FFFFFF)
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 24.dp)
                )


                // Text Field
                CTextField(hint = "Email Address", value = email, onValueChange = { email = it } )

                CTextField(hint = "Password", value = password, onValueChange = {password = it}, isPassword = true )

                Spacer(modifier = Modifier.height(24.dp))

                if (isLoading) {
                    androidx.compose.material3.CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }


                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CButton(text = "Sign In", onClick = {
                    isLoading = true
                    errorMessage = null

                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Email và mật khẩu không được để trống."
                        isLoading = false
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "Email không hợp lệ."
                        isLoading = false
                    } else {
                        auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener{ task ->
                                isLoading = false
                                if (task.isSuccessful){
                                    navController.navigate("new")
                                } else {
                                    val exception = task.exception
                                    errorMessage = when {
                                        exception?.message?.contains("password") == true -> "Mật khẩu không đúng."
                                        exception?.message?.contains("no user record") == true -> "Tài khoản không tồn tại."
                                        else -> "Đăng nhập thất bại. Vui lòng thử lại."
                                    }

                                }
                            }
                    }
                })

                Spacer(modifier = Modifier.height(8.dp))

                Text("--------- OR ---------",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 4.dp, bottom = 4.dp),
                    Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Google Sign-In Button
                GoogleStyleButton(onClick = {
                    val signInIntent: Intent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                })

                DontHaveAcc(
                    onSignupTap = {
                        navController.navigate("signup")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Quên mật khẩu?",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(800),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            showResetDialog = true
                        }
                )

                if (showResetDialog) {
                    ForgotPassword(onDismiss = { showResetDialog = false })
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}