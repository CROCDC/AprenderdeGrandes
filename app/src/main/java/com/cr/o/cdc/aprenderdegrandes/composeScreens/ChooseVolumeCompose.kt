package com.cr.o.cdc.aprenderdegrandes.composeScreens

import android.content.Context
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.cr.o.cdc.aprenderdegrandes.R
import com.cr.o.cdc.aprenderdegrandes.VolumeActivity
import com.cr.o.cdc.aprenderdegrandes.composables.AppHeader
import com.cr.o.cdc.aprenderdegrandes.ui.theme.AppTheme

@Composable
fun ChooseVolumeCompose(context: Context) {
    AppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader()
            VolumeItems(context = context)
        }
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
fun ChoseVolumeComposePreview() {
    AppTheme {
        ChooseVolumeCompose(LocalContext.current)
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
fun ChoseVolumeComposePreviewSmallScreen() {
    AppTheme {
        ChooseVolumeCompose(LocalContext.current)
    }
}