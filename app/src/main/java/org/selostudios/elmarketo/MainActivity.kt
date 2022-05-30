package org.selostudios.elmarketo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.selostudios.elmarketo.data.remote.CoinGeckoClientImpl
import org.selostudios.elmarketo.ui.theme.ElMarketoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElMarketoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val coroutineScope = rememberCoroutineScope()
    val coinGeckoClientImpl = CoinGeckoClientImpl()

    //Api tester
    Button(onClick = {
        coroutineScope.launch {
        withContext(Dispatchers.IO) {
            val list = coinGeckoClientImpl.getSupportedVSCurrencies()
            list.forEach { e->
                run {
                    println(e)
                }
            }
        }
        }
    }) {
        Text(text = "Click me to test api")
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ElMarketoTheme {
        Greeting()
    }
}