package com.cr.o.cdc.aprenderdegrandes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.cr.o.cdc.aprenderdegrandes.analitycs.FirebaseEvent
import com.cr.o.cdc.aprenderdegrandes.ui.theme.AppTheme
import com.google.firebase.analytics.FirebaseAnalytics

class FinishGameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinishGameActivityScreen()
        }
    }
}

@Composable
fun FinishGameText() {
    Text(
        lineHeight = TextUnit(40f, TextUnitType.Sp),
        fontSize = TextUnit(40f, TextUnitType.Sp),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        text = stringResource(R.string.finish_game)
    )
}

@Composable
fun PlayAgainButton() {
    val context = LocalContext.current
    val firebase = FirebaseAnalytics.getInstance(context)
    Button(
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp),
        onClick = {
            firebase.logEvent(FirebaseEvent.BTN_PLAY_AGAIN, null)
            context.startActivity(
                Intent(context, MainActivity::class.java)
            )
            (context as? Activity)?.finish()
        }
    ) {
        Text(text = stringResource(id = R.string.play_again))
    }
}

@Composable
fun RateInPlayStore() {
    Button(
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp),
        onClick = { /*TODO*/ }
    ) {
        Text(text = stringResource(id = R.string.rate_app))
    }
}

@Composable
fun FinishGameActivityScreen() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                FinishGameText()
                Spacer(modifier = Modifier.padding(top = 10.dp))
                PlayAgainButton()
                //RateInPlayStore()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinishGameTextPreview() {
    AppTheme {
        FinishGameText()
    }
}

@Preview(showBackground = true)
@Composable
fun RateInPlayStorePreview() {
    AppTheme {
        RateInPlayStore()
    }
}

@Preview(showBackground = true)
@Composable
fun PlayAgainButtonPreview() {
    AppTheme {
        PlayAgainButton()
    }
}

@Preview(showBackground = true)
@Composable
fun FinishGameActivityScreenPreview() {
    FinishGameActivityScreen()
}

