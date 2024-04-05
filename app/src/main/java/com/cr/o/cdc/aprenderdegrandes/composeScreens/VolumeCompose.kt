package com.cr.o.cdc.aprenderdegrandes.composeScreens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cr.o.cdc.aprenderdegrandes.R
import com.cr.o.cdc.aprenderdegrandes.VolumeActivity
import com.cr.o.cdc.aprenderdegrandes.VolumeActivityCompose
import com.cr.o.cdc.aprenderdegrandes.VolumeViewModel
import com.cr.o.cdc.aprenderdegrandes.composables.AppHeader
import com.cr.o.cdc.aprenderdegrandes.composables.Loading
import com.cr.o.cdc.aprenderdegrandes.states.VolumeUIState
import com.cr.o.cdc.aprenderdegrandes.ui.theme.AppTheme

@Composable
fun VolumeCompose(){
    val viewModel: VolumeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        is VolumeUIState.Loading -> Loading()
        is VolumeUIState.ShowCard -> {
            val card = (uiState as VolumeUIState.ShowCard).card
            VolumeActivityCompose(
                checkNotNull(intent.getIntExtra(VolumeActivity.ARG_VOLUME_ID, 0)),
                card.text,
                card.viewedTimes
            ) {
                viewModel.anotherCard()
            }
        }
    }
}
@Composable
fun VolumeWithCardsCompose(
    volumeId: Int,
    cardText: String,
    viewedTimes: Int,
    anotherCard: () -> Unit
) {
    AppTheme {
        Column {
            AppHeader()
            VolumeTitleCompose(volumeId = volumeId)
            CardCompose(cardText = cardText, viewedTimes = viewedTimes)
            RateCardComposable()
            AnotherButtonCardComposable(anotherCard)
        }
    }
}

@Composable
fun VolumeTitleCompose(volumeId: Int) {
    Text(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.volume, volumeId.toString())
    )
}

@Composable
fun CardCompose(cardText: String, viewedTimes: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(0.6f)
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val (txtCard, txtViewedTimes) = createRefs()
            Text(
                text = cardText,
                fontSize = 30.sp,
                modifier = Modifier.constrainAs(txtCard) {
                    top.linkTo(parent.top)
                    bottom.linkTo(txtViewedTimes.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Text(
                text = LocalContext.current.resources.getQuantityString(
                    R.plurals.viewed_n_times,
                    viewedTimes,
                    viewedTimes
                ),
                modifier = Modifier.constrainAs(txtViewedTimes) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Composable
fun AnotherButtonCardComposable(anotherCard: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { anotherCard.invoke() }
    ) {
        Text(text = stringResource(id = R.string.other))
    }
}

@Composable
@Preview(showBackground = true)
fun AnotherButtonCardComposablePreview() {
    AppTheme {
        AnotherButtonCardComposable {}
    }
}

@Composable
fun RateCardComposable() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.opinion_answer)
        )
        Row(modifier = Modifier.padding(10.dp)) {
            Image(
                modifier = Modifier
                    .height(30.dp)
                    .weight(1f)
                    .clickable {

                    },
                painter = painterResource(id = R.drawable.baseline_thumb_down_48),
                contentDescription = "TODO"
            )
            Image(
                modifier = Modifier
                    .height(30.dp)
                    .weight(1f)
                    .clickable {

                    },
                painter = painterResource(id = R.drawable.baseline_thumb_up_48),
                contentDescription = "TODO"
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RateCardComposablePreview() {
    AppTheme {
        RateCardComposable()
    }
}

@Composable
@Preview(showBackground = true, widthDp = 200, heightDp = 200)
fun CardComposePreview() {
    AppTheme {
        CardCompose("¿Sentís que tenés un propósito en la vida? ¿Cual es?", 3)
    }
}

@Composable
@Preview(showBackground = true)
fun VolumeTitleComposePreview() {
    AppTheme {
        VolumeTitleCompose(1)
    }
}

@Composable
@Preview(showBackground = true, widthDp = 410, heightDp = 880)
fun VolumeActivityComposePreview() {
    VolumeWithCardsCompose(
        1,
        "¿Sentís que tenés un propósito en la vida? ¿Cual es?",
        3
    ) {

    }
}

@Composable
@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun VolumeActivityComposePreviewDarkMode() {
    VolumeWithCardsCompose(
        1,
        "¿Sentís que tenés un propósito en la vida? ¿Cual es?",
        3
    ) {

    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
fun VolumeActivityComposePreviewSmallScreen() {
    VolumeWithCardsCompose(
        1,
        "¿Sentís que tenés un propósito en la vida? ¿Cual es?",
        3
    ) {
    }
}
