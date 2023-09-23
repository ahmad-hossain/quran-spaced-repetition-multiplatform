package com.github.ahmad_hossain.quranhifzrevision.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.github.ahmad_hossain.quranhifzrevision.Greeting
import com.github.ahmad_hossain.quranhifzrevision.Strings
import com.github.ahmad_hossain.quranhifzrevision.android.ui.theme.QuranHifzRevisionTheme
import dev.icerock.moko.resources.StringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuranHifzRevisionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Composable
fun stringResource(id: StringResource, vararg args: Any): String {
    return Strings(LocalContext.current).get(id, args.toList())
}

@Preview
@Composable
fun DefaultPreview() {
    QuranHifzRevisionTheme {
        GreetingView("Hello, Android!")
    }
}
