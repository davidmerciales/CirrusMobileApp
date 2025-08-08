package com.example.cirrusmobileapp.presentation.screens.signIn

import CommonTextField
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.example.cirrusmobileapp.R
import com.example.cirrusmobileapp.presentation.navigation.Destinations
import com.example.cirrusmobileapp.presentation.viewmodel.catalog.CatalogContract
import com.example.cirrusmobileapp.presentation.viewmodel.catalog.CatalogViewModel
import com.example.cirrusmobileapp.presentation.viewmodel.signin.SignInContract
import com.example.cirrusmobileapp.presentation.viewmodel.signin.SignInViewModel

@Composable
fun LoginScreen(
    navController: NavController,
){
    val signInViewModel : SignInViewModel = hiltViewModel()
    val state by signInViewModel.uiState.collectAsState()
    val context = LocalContext.current
    var isLoginSuccess by remember { mutableStateOf(false) }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isRememberMe by remember { mutableStateOf(false) }


    val catalogViewModel: CatalogViewModel = hiltViewModel()

    LaunchedEffect(signInViewModel) {
        signInViewModel.effect.collect { effect ->
            when (effect) {
                is SignInContract.Effect.NavigateToSync -> {
                    navController.navigate(Destinations.SyncScreen.route)
                }
                is SignInContract.Effect.ShowError -> {

                }
                is SignInContract.Effect.ShowLoginSuccess -> {
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ){
        Row(
            modifier = Modifier
                .background(color = Color.White, RoundedCornerShape(20.dp))
                .fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Column (
                modifier = Modifier
                    .weight(.48f)
                    .fillMaxHeight()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier
                        .weight(.2f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.jdi_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(90.dp),
                        contentScale = ContentScale.FillBounds
                    )

                    Text(
                        text = "Jardine Distribution, Inc.",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.Black.copy(.8f)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(.14f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome to JDI Sales.",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        lineHeight = 1.4.em,
                        color = Color.Black.copy(.8f)
                    )
                    Text(
                        text = "Enter your registered credentials.",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 1.2.em,
                        color = Color.Black.copy(.8f)
                    )
                }

                Column (
                    modifier = Modifier
                        .weight(.38f)
                        .fillMaxWidth(.78f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    CommonTextField(
                        value = state.email,
                        onValueChange = { signInViewModel.updateEmail(it) },
                        label = "Email Address",
                    )

                    CommonTextField(
                        value = state.password,
                        onValueChange = { signInViewModel.updatePassword(it)  },
                        label = "Password",
                        trailingIcon = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        onTrailingIconClick = { isPasswordVisible = !isPasswordVisible },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    Box(
                        modifier = Modifier
                            .height(46.dp)
                            .fillMaxWidth()
                            .background(
                                color = Color.Black,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                catalogViewModel.processIntent(CatalogContract.Intent.LoadProducts)
                                signInViewModel.processIntent(SignInContract.Intent.SignIn)
                            },
                        contentAlignment = Alignment.Center
                    ){
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                text = "Sign In",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400,
                                lineHeight = 1.2.em,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    isRememberMe = !isRememberMe
                                }
                                .size(20.dp)
                                .border(
                                    1.2.dp,
                                    Color(0xFF757575).copy(.4f),
                                    RoundedCornerShape(4.dp)
                                )
                        ){
                            if (isRememberMe)
                            Icon(
                                Icons.Default.Check,
                                tint = Color(0xFF0014ab),
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.Center),
                                contentDescription = "check"
                            )
                        }

                        Spacer(Modifier.width(8.dp))

                        Text(
                            text = "Remember Me",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W400,
                            lineHeight = 1.2.em,
                            color = Color.Black.copy(.6f),
                            modifier = Modifier
                                .weight(1f)
                        )


                        Text(
                            text = "Forgot your Password?",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W400,
                            lineHeight = 1.2.em,
                            color = Color.Black.copy(.6f),
                            modifier = Modifier
                                .clickable{

                                }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(.25f)
                        .fillMaxWidth(.58f),
                    contentAlignment = Alignment.Center
                ){
                    Row {
                        Text(
                            text = "Copyright © 2025. All Rights Reserved. ",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W300,
                            lineHeight = 1.2.em,
                            color = Color.Black,
                        )

                        Text(
                            text = "Terms",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W400,
                            lineHeight = 1.2.em,
                            color = Color.Black,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .clickable{

                                }
                        )

                        Text(
                            text = " & ",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W300,
                            lineHeight = 1.2.em,
                            color = Color.Black,
                            modifier = Modifier
                                .clickable{

                                }
                        )

                        Text(
                            text = "Privacy Policy",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W400,
                            lineHeight = 1.2.em,
                            color = Color.Black,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .clickable{

                                }
                        )

                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.login_bg),
                contentDescription = "App Logo",
                modifier = Modifier
                    .weight(.52f)
                    .padding(start = 10.dp)
                    .fillMaxHeight()
                    .background(
                        Color.Blue,
                        RoundedCornerShape(
                            20.dp
                        )
                    ),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
