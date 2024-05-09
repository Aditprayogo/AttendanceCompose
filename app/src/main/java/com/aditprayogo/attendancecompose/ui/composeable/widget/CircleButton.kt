package com.aditprayogo.attendancecompose.ui.composeable.widget

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aditprayogo.attendancecompose.ui.theme.AttendanceComposeTheme
import com.aditprayogo.attendancecompose.ui.theme.BgMustard
import com.aditprayogo.attendancecompose.ui.theme.ButtonBgGreen

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    backgroundColor: Color,
    buttonSize: Dp = 250.dp,
    fontSize: TextUnit = 35.sp
) {
    Button(
        modifier = modifier
            .size(buttonSize),
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        )
    ) {
        Text(
            text = text,
            color = Color.White,
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = fontSize
            )
        )
    }
}

@Preview
@Composable
fun CircleButtonCheckInPreview() {
    AttendanceComposeTheme {
        CircleButton(
            onClick = {},
            text = "Check In",
            backgroundColor = ButtonBgGreen
        )
    }
}

@Preview
@Composable
fun CircleButtonCheckOutPreview() {
    AttendanceComposeTheme {
        CircleButton(
            onClick = { },
            text = "Check Out",
            backgroundColor = BgMustard
        )
    }
}