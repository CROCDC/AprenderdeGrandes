package com.cr.o.cdc.aprenderdegrandes

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.cr.o.cdc.aprenderdegrandes.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainActivityCompose(LocalContext.current)
            }
        }
    }
}

@Composable
fun MainActivityCompose(context: Context) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader()
        VolumeItems(context = context)
    }
}

@Composable
fun AppHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.mipmap.ic_launcher),
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        )

        Text(
            fontSize = TextUnit(20f, TextUnitType.Sp),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(id = R.string.title)
        )
    }
}

@Composable
fun VolumeItems(context: Context) {
    BoxWithConstraints {
        val distanceTop = maxHeight * 0.30f
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = distanceTop)
        ) {
            VolumeButton(1, R.string.volume_1, context)
            VolumeButton(2, R.string.volume_2, context)
        }
    }

}

@Composable
fun VolumeButton(id: Int, name: Int, context: Context) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = {
            context.startActivity(VolumeActivity.getIntent(context = context, id))
        }) {
        Text(
            fontSize = TextUnit(20f, TextUnitType.Sp),
            text = stringResource(id = name)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VolumeItemsPreview() {
    AppTheme {
        VolumeItems(LocalContext.current)
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityComposePreview() {
    AppTheme {
        MainActivityCompose(LocalContext.current)
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
fun MainActivityComposePreviewSmallScreen() {
    AppTheme {
        MainActivityCompose(LocalContext.current)
    }
}

@Preview(showBackground = true)
@Composable
fun AppHeaderPreview() {
    AppTheme {
        AppHeader()
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AppHeaderPreviewDarkMode() {
    AppTheme {
        AppHeader()
    }
}
