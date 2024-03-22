package com.cr.o.cdc.aprenderdegrandes.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cr.o.cdc.aprenderdegrandes.ui.theme.AppTheme

@Composable
fun Loading() {
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier =
                Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoadingPreview() {
    Loading()
}