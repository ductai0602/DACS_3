package com.example.dacs3_new.Activity.Login.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPassword(
    onDismiss: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var resetMessage by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSending by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text("Đặt lại mật khẩu", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text("Nhập email bạn đã đăng ký để nhận liên kết đặt lại mật khẩu.")
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        resetMessage = null
                        errorMessage = null
                    },
                    label = { Text("Email") },
                    singleLine = true
                )
                if (resetMessage != null) {
                    Text(resetMessage!!, color = Color(0xFF4CAF50), fontSize = 14.sp)
                }
                if (errorMessage != null) {
                    Text(errorMessage!!, color = Color.Red, fontSize = 14.sp)
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    errorMessage = null
                    resetMessage = null

                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "Email không hợp lệ."
                        return@TextButton
                    }

                    isSending = true
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            isSending = false
                            if (task.isSuccessful) {
                                resetMessage = "Đã gửi email đặt lại mật khẩu. Kiểm tra hộp thư của bạn."
                            } else {
                                errorMessage = "Không thể gửi email. Vui lòng thử lại."
                            }
                        }
                },
                enabled = !isSending
            ) {
                Text(if (isSending) "Đang gửi..." else "Gửi")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Huỷ")
            }
        }
    )
}