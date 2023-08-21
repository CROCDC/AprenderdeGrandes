package com.cr.o.cdc.aprenderdegrandes

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val txt = findViewById<TextView>(R.id.txt)
        val progressBar = findViewById<View>(R.id.progress_circular)
        lifecycle.coroutineScope.launch {
            viewModel.showCard.collectLatest {
                it?.let { txt.setTextAnimation(it.text) }
            }
        }
        lifecycle.coroutineScope.launch {
            viewModel.notViewedCards.collectLatest {
                progressBar.isVisible = it is Resource.Loading
            }
        }
        findViewById<View>(R.id.btn).setOnClickListener {
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

    fun View.fadInAnimation(duration: Long = 300, completion: (() -> Unit)? = null) {
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