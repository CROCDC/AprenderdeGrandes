package com.cr.o.cdc.aprenderdegrandes.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.cr.o.cdc.aprenderdegrandes.R
import com.cr.o.cdc.aprenderdegrandes.ui.theme.AppTheme

@Composable
fun AppHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.mipmap.ic_launcher),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(0.2f)
                .aspectRatio(1f)
        )

        Text(
            fontSize = TextUnit(20f, TextUnitType.Sp),
            color = MaterialTheme.colorScheme.onSurface,
            text = stringResource(id = R.string.title)
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 320, heightDp = 480)
fun AppHeaderPreview() {
    AppTheme {
        AppHeader()
    }
}

@Composable
@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun AppHeaderPreviewDarkMode() {
    AppTheme {
        AppHeader()
    }
}
