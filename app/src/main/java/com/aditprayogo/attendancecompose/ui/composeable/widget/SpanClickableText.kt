package com.aditprayogo.attendancecompose.ui.composeable.widget

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.aditprayogo.attendancecompose.ui.theme.AttBlue
import com.aditprayogo.attendancecompose.ui.theme.AttendanceComposeTheme
import com.aditprayogo.attendancecompose.ui.theme.TextGrayDarkish

private const val CLICK_TAG = "CLICK_TAG"

@Composable
fun SpanClickableText(
    modifier: Modifier = Modifier,
    onSpanTextClick: () -> Unit,
    regularText: String,
    clickableText: String,
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = 14.sp
) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = TextGrayDarkish,
            )
        ) {
            append("$regularText ")
        }
        pushStringAnnotation(
            tag = CLICK_TAG, annotation = ""
        )
        withStyle(
            style = SpanStyle(
                color = AttBlue
            )
        ) {
            append(clickableText)
        }
        pop()
    }

    ClickableText(
        modifier = modifier,
        text = annotatedText,
        onClick = { offset: Int ->
            annotatedText.getStringAnnotations(
                tag = CLICK_TAG, start = offset, end = offset
            ).firstOrNull()?.let {
                onSpanTextClick()
            }
        },
        style = LocalTextStyle.current.copy(
            textAlign = textAlign,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SpanClickableTextPreview() {
    AttendanceComposeTheme {
        SpanClickableText(
            onSpanTextClick = { },
            regularText = "Memberi Makan?",
            clickableText = "Click"
        )
    }
}