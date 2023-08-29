package com.cr.o.cdc.aprenderdegrandes

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import com.cr.o.cdc.aprenderdegrandes.analitycs.FirebaseEvent
import com.cr.o.cdc.aprenderdegrandes.analitycs.MyFirebaseAnalytics
import com.cr.o.cdc.aprenderdegrandes.databinding.ActivityMainBinding
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var analytics: MyFirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.txt.movementMethod = ScrollingMovementMethod()
        lifecycle.coroutineScope.launch {
            viewModel.showCard.collectLatest {
                it?.let { card ->
                    binding.txt.setTextAnimation(card.text)
                    analytics.trackViewCard(card)
                    binding.txtViewedNTimes.setTextAnimation(
                        getString(R.string.viewed_n_times, card.viewedTimes)
                    )
                    binding.imgThumbDown.setOnClickListener {
                        analytics.voteCard(FirebaseEvent.VOTE_DOWN_CARD, card.id)
                    }
                    binding.imgThumbUp.setOnClickListener {
                        analytics.voteCard(FirebaseEvent.VOTE_UP_CARD, card.id)
                    }
                }
            }
        }
        lifecycle.coroutineScope.launch {
            viewModel.cards.collectLatest {
                binding.progressCircular.isVisible = it is Resource.Loading
            }
        }
        lifecycle.coroutineScope.launch {
            viewModel.notMoreCards.collectLatest {
                if (it) {
                    binding.btn.setOnClickListener {
                        analytics.trackEvent(FirebaseEvent.BTN_FINISH_GAME)
                        startActivity(
                            Intent(
                                this@MainActivity,
                                FinishGameActivity::class.java
                            )
                        )
                        finish()
                    }
                }
            }
        }
        binding.btn.setOnClickListener {
            analytics.trackEvent(FirebaseEvent.BTN_ANOTHER_CARD)
            viewModel.anotherCard()
        }
    }

    private fun TextView.setTextAnimation(
        text: String,
        duration: Long = 300,
        completion: (() -> Unit)? = null
    ) {
        fadOutAnimation(duration) {
            this.text = text
            fadInAnimation(duration) {
                completion?.let {
                    it()
                }
            }
        }
    }

    private fun View.fadOutAnimation(
        duration: Long = 300,
        visibility: Int = View.INVISIBLE,
        completion: (() -> Unit)? = null
    ) {
        animate()
            .alpha(0f)
            .setDuration(duration)
            .withEndAction {
                this.visibility = visibility
                completion?.let {
                    it()
                }
            }
    }

    private fun View.fadInAnimation(duration: Long = 300, completion: (() -> Unit)? = null) {
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(duration)
            .withEndAction {
                completion?.let {
                    it()
                }
            }
    }
}