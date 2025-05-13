package com.example.dacs3_new.Activity.Login.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GoogleStyleButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, Color(0xFFDDDDDD))
    ) {
        Text(
            buildAnnotatedString {
                append("Đăng nhập ")
                withStyle(style = SpanStyle(color = Color(0xFF4285F4))) { append("G") }
                withStyle(style = SpanStyle(color = Color(0xFFDB4437))) { append("o") }
                withStyle(style = SpanStyle(color = Color(0xFFF4B400))) { append("o") }
                withStyle(style = SpanStyle(color = Color(0xFF4285F4))) { append("g") }
                withStyle(style = SpanStyle(color = Color(0xFF0F9D58))) { append("l") }
                withStyle(style = SpanStyle(color = Color(0xFFDB4437))) { append("e") }
            },
            fontSize = 22.sp,
            fontWeight = FontWeight(500)
        )
    }
}